package com.thebuyback.eve.service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;
import static java.util.Arrays.asList;

import com.codahale.metrics.annotation.Timed;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thebuyback.eve.domain.Appraisal;
import com.thebuyback.eve.domain.CapitalShip;
import com.thebuyback.eve.domain.CapitalShipStatus;
import com.thebuyback.eve.domain.Contract;
import com.thebuyback.eve.domain.ItemBuybackRate;
import com.thebuyback.eve.domain.ItemWithQuantity;
import com.thebuyback.eve.domain.Token;
import com.thebuyback.eve.repository.CapitalShipRepository;
import com.thebuyback.eve.repository.ContractRepository;
import com.thebuyback.eve.repository.ItemBuybackRateRepository;
import com.thebuyback.eve.repository.TokenRepository;
import static com.thebuyback.eve.web.rest.ContractsResource.THE_BUYBACK;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * ContractParser
 *
 * Created on 08.11.2017
 */
@Service
public class ContractParser {

    private final Logger log = LoggerFactory.getLogger(getClass());

    public static final String PARSER_CLIENT = "e072368e841242288618e4c718dc6c5c";

    private final JsonRequestService requestService;
    private final TokenRepository tokenRepository;
    private final ContractRepository contractRepository;
    private final TypeNameService typeNameService;
    private final CapitalShipRepository capitalShipRepository;
    private final ItemBuybackRateRepository buybackRateRepository;

    public ContractParser(final JsonRequestService requestService,
                          final TokenRepository tokenRepository,
                          final ContractRepository contractRepository,
                          final TypeNameService typeNameService,
                          final CapitalShipRepository capitalShipRepository,
                          final ItemBuybackRateRepository buybackRateRepository) {
        this.requestService = requestService;
        this.tokenRepository = tokenRepository;
        this.contractRepository = contractRepository;
        this.typeNameService = typeNameService;
        this.capitalShipRepository = capitalShipRepository;
        this.buybackRateRepository = buybackRateRepository;
    }

    @Scheduled(cron = "0 1/10 * * * *")
    @Async
    @Timed
    public void loadNonCompletedContracts() throws UnirestException {
        final Token token = tokenRepository.findByClientId(PARSER_CLIENT).get(0);
        final String accessToken = requestService.getAccessToken(token);
        final Optional<JsonNode> corpContracts = requestService.getCorpContracts(accessToken);
        if (corpContracts.isPresent()) {
            JSONArray contractArray = corpContracts.get().getArray();
            for (int i = 0; i < contractArray.length(); i++) {
                parseContract(accessToken, contractArray, i);
            }
            log.info("Contract parsing complete.");
        } else {
            log.warn("ESI did not return any contracts.");
        }
    }

    @Scheduled(cron = "0 5/10 * * * *")
    @Async
    @Timed
    public void loadOutstandingCaps() {
        List<CapitalShip> outstandingCaps = contractRepository
            .findAllByStatusAndAssigneeIdAndIssuerCorporationId("outstanding", 0L, THE_BUYBACK)
            .stream().map(this::mapToCapitalShip).collect(Collectors.toList());

        capitalShipRepository.deleteByStatus(CapitalShipStatus.PUBLIC_CONTRACT);
        capitalShipRepository.save(outstandingCaps);
    }

    private CapitalShip mapToCapitalShip(final Contract contract) {
        Entry<Integer, Integer> first = contract.getItems().entrySet().iterator().next();
        int typeId = first.getKey();
        final String typeName = typeNameService.getTypeName(typeId);
        return new CapitalShip(CapitalShipStatus.PUBLIC_CONTRACT, contract.getPrice(), typeId, typeName);
    }

    private void parseContract(final String accessToken, final JSONArray contractArray, final int i)
        throws UnirestException {
        final JSONObject jsonContract = contractArray.getJSONObject(i);

        long contractId = jsonContract.getLong("contract_id");
        long issuerId = jsonContract.getLong("issuer_id");
        long assigneeId = jsonContract.getLong("assignee_id");
        long issuerCorporationId = jsonContract.getLong("issuer_corporation_id");

        if (!"item_exchange".equals(jsonContract.getString("type"))) {
            return;
        }

        if (isAssignedToBraveCollective(assigneeId) && !isFromTheBuyback(issuerCorporationId)) {
            return;
        }

        final Optional<Contract> optional = contractRepository.findById(contractId);
        String appraisalLink;
        double buyValue = 0.0;
        double sellValue = 0.0;
        final String[] client = {null};
        Map<Integer, Integer> items;
        boolean declineMailSent;
        boolean approved;
        if (optional.isPresent()) {
            Contract contract = optional.get();
            if (asList("finished", "rejected", "deleted").contains(contract.getStatus())) {
                // skip contracts that are done and already in the db
                log.debug("Skipping {} as it already exists.", contractId);
                return;
            }
            items = contract.getItems();

            appraisalLink = contract.getAppraisalLink();
            buyValue = contract.getBuyValue();
            sellValue = contract.getSellValue();
            client[0] = contract.getClient();
            declineMailSent = contract.isDeclineMailSent();
            approved = contract.isApproved();

            // delete existing contract as we'll overwrite it in a second
            contractRepository.delete(contract);
        } else {
            items = getItemsForContract(contractId, accessToken);

            appraisalLink = AppraisalUtil.getLinkFromRaw(getRaw(items));
            if (null != appraisalLink) {
                buyValue = AppraisalUtil.getBuy(appraisalLink);
                sellValue = AppraisalUtil.getSell(appraisalLink);
            }

            Optional<JsonNode> characterName = requestService.getCharacterName(issuerId);
            characterName.ifPresent(jsonNode -> client[0] = jsonNode.getArray().getJSONObject(0)
                                                                    .getString("character_name"));
            declineMailSent = false;
            approved = false;
        }

        String status = jsonContract.getString("status");
        long startLocationId = jsonContract.getLong("start_location_id");
        double price = jsonContract.getDouble("price");
        String title = null;
        if (jsonContract.has("title")) {
            title = jsonContract.getString("title");
        }
        Instant dateIssued = Instant.parse(jsonContract.getString("date_issued"));
        Instant dateCompleted = null;
        if (jsonContract.has("date_completed")) {
            dateCompleted = Instant.parse(jsonContract.getString("date_completed"));
        }

        final Contract contract = new Contract(contractId, issuerId, issuerCorporationId, assigneeId, status,
                                               startLocationId, price, items, appraisalLink, buyValue,
                                               sellValue, title, dateIssued, dateCompleted, client[0],
                                               declineMailSent, approved);

        if (!isAssignedToBraveCollective(assigneeId)) {
            contract.setBuybackPrice(calcBuybackRate(contractId, accessToken));
        }

        contractRepository.save(contract);
        log.debug("Saved contract {}.", contractId);

    }

    private double calcBuybackRate(final long contractId, final String accessToken) {
        StringBuilder raw = new StringBuilder();
        Optional<JsonNode> optional = requestService.getCorpContractItems(contractId, accessToken);
        if (optional.isPresent()) {
            JSONArray array = optional.get().getArray();
            for (int i = 0; i < array.length(); i++) {
                JSONObject item = array.getJSONObject(i);
                long typeId = item.getLong("type_id");
                long quantity = item.getLong("quantity");
                String typeName = typeNameService.getTypeName(typeId);
                raw.append(typeName).append(" x").append(quantity).append('\n');
            }
        } else {
            return 0;
        }
        final List<ItemWithQuantity> items;
        try {
            String linkFromRaw = AppraisalUtil.getLinkFromRaw(raw.toString());
            items = AppraisalUtil.getItems(linkFromRaw);
        } catch (UnirestException e) {
            log.error("Failed to get appraisal.", e);
            return 0;
        }

        double total = 0;
        for (ItemWithQuantity item : items) {
            final long typeId = item.getTypeID();
            final long quantity = item.getQuantity();
            double buy = item.getJitaBuyPerUnit();
            ItemBuybackRate itemRate = buybackRateRepository.findOneByTypeId(typeId);
            if (null == itemRate) {
                total += buy * 0.9 * quantity;
            } else {
                total += buy * itemRate.getRate() * quantity;
            }
        }
        return total;
    }

    private boolean isFromTheBuyback(final long assigneeId) {
        return assigneeId == THE_BUYBACK;
    }

    private boolean isAssignedToBraveCollective(final long assigneeId) {
        return assigneeId == 99003214L;
    }

    private String getRaw(final Map<Integer, Integer> items) {
        return items.entrySet().stream().map(entry -> {
            String typeName = typeNameService.getTypeName(entry.getKey());
            return typeName + " x" + entry.getValue();
        }).collect(Collectors.joining("\n"));
    }

    private Map<Integer, Integer> getItemsForContract(final long contractId, final String accessToken) {
        final Map<Integer, Integer> result = new HashMap<>();
        Optional<JsonNode> corpContractItems = requestService.getCorpContractItems(contractId, accessToken);
        corpContractItems.ifPresent(jsonNode -> {
            JSONArray jsonArray = jsonNode.getArray();
            log.debug("Contract {} has {} items.", contractId, jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int typeId = jsonObject.getInt("type_id");
                int quantity = jsonObject.getInt("quantity");

                if (result.containsKey(typeId)) {
                    quantity += result.get(typeId);
                }
                result.put(typeId, quantity);
            }
        });
        return result;
    }
}

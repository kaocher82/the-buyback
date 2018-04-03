package com.thebuyback.eve.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thebuyback.eve.domain.Asset;
import com.thebuyback.eve.domain.NetWorth;
import com.thebuyback.eve.domain.Token;
import com.thebuyback.eve.repository.AssetRepository;
import com.thebuyback.eve.repository.NetWorthHistoryRepository;
import com.thebuyback.eve.repository.TokenRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import io.github.jhipster.config.JHipsterConstants;

@Service
public class NetWorthParser {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private static final String THE_BUYBACK_APP = "e072368e841242288618e4c718dc6c5c";
    private static final long DELAY = 1000L * 60L * 60L; // 1 hour

    private final JsonRequestService requestService;
    private final AssetRepository assetRepository;
    private final TokenRepository tokenRepository;
    private final Environment env;
    private final NetWorthHistoryRepository netWorthHistoryRepository;

    public NetWorthParser(final JsonRequestService requestService, final AssetRepository assetRepository,
                          final TokenRepository tokenRepository, final Environment env,
                          final NetWorthHistoryRepository netWorthHistoryRepository) {
        this.requestService = requestService;
        this.assetRepository = assetRepository;
        this.tokenRepository = tokenRepository;
        this.env = env;
        this.netWorthHistoryRepository = netWorthHistoryRepository;
    }

    @Async
    @Scheduled(fixedDelay = DELAY)
    public void refreshNetWorth() {
        if (env.acceptsProfiles(JHipsterConstants.SPRING_PROFILE_DEVELOPMENT)) {
            return;
        }

        log.info("Refreshing net worth.");
        final Token token = tokenRepository.findByClientId(THE_BUYBACK_APP).get(0);
        final String accessToken;
        try {
            accessToken = requestService.getAccessToken(token);
        } catch (UnirestException e) {
            log.error("Failed to get access token for refreshAssets.", e);
            return;
        }

        addAssetAndOreHistory();
        log.info("Asset history added.");
        addWalletHistory(accessToken);
        log.info("Wallet history added.");
        addMarketHistory(accessToken);
        log.info("Market history added.");

        log.info("Refreshing net worth complete.");
    }

    private void addMarketHistory(final String accessToken) {
        final Optional<JsonNode> node = requestService.getCorpMarketOrders(accessToken);
        if (node.isPresent()) {
            double total = 0.0;
            final JSONArray ordersArray = node.get().getArray();
            for (int i = 0; i < ordersArray.length(); i++) {
                final JSONObject order = ordersArray.getJSONObject(i);
                final boolean isBuyOrder = order.has("is_buy_order") ? order.getBoolean("is_buy_order") : false;
                final int volumeRemain = order.getInt("volume_remain");
                final double price = order.getDouble("price");
                final double escrow = order.has("escrow") ? order.getDouble("escrow") : 0.0;
                if (isBuyOrder) {
                    total += escrow;
                } else {
                    total += price * volumeRemain;
                }
            }
            saveMarketOrdersToNetWorth(total);
        } else {
            log.warn("getCorpMarketOrders did not return a result.");
        }
    }

    private void saveMarketOrdersToNetWorth(final double amount) {
        final Optional<NetWorth> optionalHistory = netWorthHistoryRepository.findOneByDate(LocalDate.now());
        if (optionalHistory.isPresent()) {
            final NetWorth netWorth = optionalHistory.get();
            if (netWorth.getMarketOrdersLow() == null || netWorth.getMarketOrdersLow() > amount) {
                netWorth.setMarketOrdersLow(amount);
            }
            if (netWorth.getMarketOrdersHigh() == null || netWorth.getMarketOrdersHigh() < amount) {
                netWorth.setMarketOrdersHigh(amount);
            }
            netWorth.setMarketOrdersLatest(amount);
            netWorthHistoryRepository.save(netWorth);
        } else {
            final NetWorth entity = new NetWorth(LocalDate.now());
            entity.setMarketOrdersHigh(amount);
            entity.setMarketOrdersLow(amount);
            entity.setMarketOrdersLatest(amount);
            netWorthHistoryRepository.save(entity);
        }
    }

    private void addWalletHistory(final String accessToken) {


        final Optional<JsonNode> node = requestService.getMasterWalletBalance(accessToken);
        if (node.isPresent()) {
            final JSONArray divisionArray = node.get().getArray();
            Double balance = null;
            for (int i = 0; i < divisionArray.length(); i++) {
                if (divisionArray.getJSONObject(i).getInt("division") != 1) {
                    continue;
                }
                balance = divisionArray.getJSONObject(i).getDouble("balance");
            }
            if (balance == null) {
                log.warn("Could not find the balance for the master wallet.");
            } else {
                log.info("Master wallet balance is {}.", balance);
                saveWalletToNetWorth(balance);
            }
        } else {
            log.warn("getMasterWalletBalance did not return a result.");
        }
    }

    private void saveWalletToNetWorth(final Double balance) {
        final Optional<NetWorth> optionalHistory = netWorthHistoryRepository.findOneByDate(LocalDate.now());
        if (optionalHistory.isPresent()) {
            final NetWorth netWorth = optionalHistory.get();
            if (netWorth.getWalletLow() == null || netWorth.getWalletLow() > balance) {
                netWorth.setWalletLow(balance);
            }
            if (netWorth.getWalletHigh() == null || netWorth.getWalletHigh() < balance) {
                netWorth.setWalletHigh(balance);
            }
            netWorth.setWalletLatest(balance);
            netWorthHistoryRepository.save(netWorth);
        } else {
            final NetWorth entity = new NetWorth(LocalDate.now());
            entity.setWalletHigh(balance);
            entity.setWalletLow(balance);
            entity.setWalletLatest(balance);
            netWorthHistoryRepository.save(entity);
        }
    }

    private void addAssetAndOreHistory() {
        final List<Asset> assets = assetRepository.findAll().stream()
                                                     .filter(a -> a.getPrice() != null)
                                                     .filter(a -> a.getTypeName() != null)
                                                     .filter(a -> !a.getTypeName().contains("Blueprint"))
                                                     .collect(Collectors.toList());

        final double currentAssetsValue = assets.stream()
                                                .filter(asset -> !asset.getTypeName().startsWith("Compressed"))
                                                .mapToDouble(asset -> asset.getPrice() * asset.getQuantity()).sum();
        final double currentOreValue = assets.stream()
                                                .filter(asset -> asset.getTypeName().startsWith("Compressed"))
                                                .mapToDouble(asset -> asset.getPrice() * asset.getQuantity()).sum();

        final Optional<NetWorth> optionalHistory = netWorthHistoryRepository.findOneByDate(LocalDate.now());
        if (optionalHistory.isPresent()) {
            final NetWorth netWorth = optionalHistory.get();
            if (netWorth.getAssetLow() == null || netWorth.getAssetLow() > currentAssetsValue) {
                netWorth.setAssetLow(currentAssetsValue);
            } else if (netWorth.getAssetHigh() == null || netWorth.getAssetHigh() < currentAssetsValue) {
                netWorth.setAssetHigh(currentAssetsValue);
            }
            if (netWorth.getCompressedOreLow() == null || netWorth.getCompressedOreLow() > currentOreValue) {
                netWorth.setCompressedOreLow(currentOreValue);
            } else if (netWorth.getCompressedOreHigh() == null || netWorth.getCompressedOreHigh() < currentOreValue) {
                netWorth.setCompressedOreHigh(currentOreValue);
            }
            netWorth.setAssetLatest(currentAssetsValue);
            netWorth.setCompressedOreLatest(currentOreValue);
            netWorthHistoryRepository.save(netWorth);
        } else {
            final NetWorth netWorth = new NetWorth(LocalDate.now(), currentAssetsValue, currentAssetsValue);
            netWorth.setCompressedOreHigh(currentOreValue);
            netWorth.setCompressedOreLow(currentOreValue);
            netWorth.setAssetLatest(currentAssetsValue);
            netWorth.setCompressedOreLatest(currentOreValue);
            netWorthHistoryRepository.save(netWorth);
        }
    }

}

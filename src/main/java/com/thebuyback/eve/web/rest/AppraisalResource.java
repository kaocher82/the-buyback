package com.thebuyback.eve.web.rest;

import java.util.List;
import java.util.stream.Collectors;

import com.thebuyback.eve.domain.Appraisal;
import com.codahale.metrics.annotation.Timed;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thebuyback.eve.domain.ItemBuybackRate;
import com.thebuyback.eve.domain.ItemWithQuantity;
import com.thebuyback.eve.service.AppraisalUtil;
import com.thebuyback.eve.service.ItemBuybackRateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * REST controller for executing appraisal requests.
 */
@RestController
@RequestMapping("/api/appraisal")
public class AppraisalResource {

    private final Logger log = LoggerFactory.getLogger(AppraisalResource.class);
    private final ItemBuybackRateService itemRateService;

    public AppraisalResource(final ItemBuybackRateService itemRateService) {
        this.itemRateService = itemRateService;
    }

    /**
     * POST  / : Execute an appraisal request.
     *
     * @param appraisal the appraisal to request/update
     * @return the executed/updated Appraisal
     */
    @PostMapping
    @Timed
    public ResponseEntity<Appraisal> requestAppraisal(@RequestBody Appraisal appraisal) {
        log.debug("REST request to perform an appraisal : {}", appraisal);

        if (appraisal.getAdditionalRaw().isEmpty()) {
            return ResponseEntity.status(400).build();
        }

        try {
            executeRequestAndUpdateAppraisal(appraisal);
        } catch (UnirestException e) {
            log.error("Unirest failed.", e);
            return ResponseEntity.status(500).build();
        }

        return ResponseEntity.ok().body(appraisal);
    }

    private void executeRequestAndUpdateAppraisal(final Appraisal appraisal) throws UnirestException {
        appraisal.updateRaw();
        appraisal.setLink(AppraisalUtil.getLinkFromRaw(appraisal.getRaw()));
        appraisal.setJitaBuy(AppraisalUtil.getBuy(appraisal.getLink()));
        appraisal.setJitaSell(AppraisalUtil.getSell(appraisal.getLink()));
        appraisal.setItems(AppraisalUtil.getItems(appraisal.getLink()));
        final List<Long> typeIds = appraisal.getItems().stream().map(ItemWithQuantity::getTypeID)
                                               .collect(Collectors.toList());
        final List<ItemBuybackRate> rates = itemRateService.getRates(typeIds);
        double buybackPrice = 0;
        for (ItemWithQuantity item : appraisal.getItems()) {
            for (ItemBuybackRate rate : rates) {
                if (item.getTypeID() == rate.getTypeId()) {
                    item.setRate(rate.getRate());
                    break;
                }
            }
            if (0 == item.getRate()) {
                item.setRate(0.9);
            }
            buybackPrice += item.getQuantity() * item.getJitaBuyPerUnit() * item.getRate();
        }
        appraisal.setBuybackPrice(buybackPrice);
    }
}

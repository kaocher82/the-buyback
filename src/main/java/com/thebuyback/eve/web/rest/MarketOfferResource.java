package com.thebuyback.eve.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thebuyback.eve.domain.MarketOffer;

import com.thebuyback.eve.domain.enumeration.MarketOfferCategory;
import com.thebuyback.eve.domain.enumeration.MarketOfferType;
import com.thebuyback.eve.repository.MarketOfferRepository;
import com.thebuyback.eve.security.AuthoritiesConstants;
import com.thebuyback.eve.security.SecurityUtils;
import com.thebuyback.eve.web.rest.util.HeaderUtil;
import com.thebuyback.eve.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing MarketOffer.
 */
@RestController
@RequestMapping("/api")
public class MarketOfferResource {

    private final Logger log = LoggerFactory.getLogger(MarketOfferResource.class);

    private static final String ENTITY_NAME = "marketOffer";

    private final MarketOfferRepository marketOfferRepository;
    public MarketOfferResource(MarketOfferRepository marketOfferRepository) {
        this.marketOfferRepository = marketOfferRepository;
    }

    /**
     * POST  /market-offers : Create a new marketOffer.
     *
     * @param marketOffer the marketOffer to create
     * @return the ResponseEntity with status 201 (Created) and with body the new marketOffer, or with status 400 (Bad Request) if the marketOffer has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/market-offers")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<MarketOffer> createMarketOffer(@RequestBody MarketOffer marketOffer) throws URISyntaxException {
        log.debug("REST request to save MarketOffer : {}", marketOffer);
        if (marketOffer.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new marketOffer cannot already have an ID")).body(null);
        }

        marketOffer.setIssuer(SecurityUtils.getCurrentUserLogin());
        marketOffer.setCreated(Instant.now());
        marketOffer.setExpiry(LocalDate.now().plusDays(14).atStartOfDay().toInstant(ZoneOffset.UTC));
        marketOffer.setExpiryUpdated(Instant.now());
        marketOffer.setCategory(MarketOfferCategory.NONE);

        MarketOffer result = marketOfferRepository.save(marketOffer);
        return ResponseEntity.created(new URI("/api/market-offers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /market-offers : Updates an existing marketOffer.
     *
     * @param marketOffer the marketOffer to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated marketOffer,
     * or with status 400 (Bad Request) if the marketOffer is not valid,
     * or with status 500 (Internal Server Error) if the marketOffer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/market-offers")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<MarketOffer> updateMarketOffer(@RequestBody MarketOffer marketOffer) throws URISyntaxException {
        log.debug("REST request to update MarketOffer : {}", marketOffer);
        if (marketOffer.getId() == null) {
            return createMarketOffer(marketOffer);
        }
        MarketOffer offer = marketOfferRepository.findOne(marketOffer.getId());
        if (!offer.getIssuer().equalsIgnoreCase(SecurityUtils.getCurrentUserLogin())) {
            return ResponseEntity.status(403).build();
        }
        marketOffer.setExpiry(LocalDate.now().plusDays(7).atStartOfDay().toInstant(ZoneOffset.UTC));
        marketOffer.setExpiryUpdated(Instant.now());
        MarketOffer result = marketOfferRepository.save(marketOffer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketOffer.getId().toString()))
            .body(result);
    }

    @GetMapping("/market-offers/active/{type}")
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<MarketOffer>> getActiveMarketOffers(@PathVariable("type") final MarketOfferType type) {
        List<MarketOffer> collect = marketOfferRepository.findAllByType(type).stream()
                                                         .filter(o -> o.getExpiry().isAfter(Instant.now()))
                                                         .collect(Collectors.toList());
        return new ResponseEntity<>(collect, HttpStatus.OK);
    }

    @GetMapping("/market-offers/private/{type}")
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<List<MarketOffer>> getPrivateMarketOffers(@PathVariable("type") final MarketOfferType type) {
        List<MarketOffer> collect = marketOfferRepository.findAllByTypeAndIssuer(type, SecurityUtils.getCurrentUserLogin())
                                                         .stream().collect(Collectors.toList());
        return new ResponseEntity<>(collect, HttpStatus.OK);
    }

    /**
     * GET  /market-offers : get all the marketOffers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of marketOffers in body
     */
    @GetMapping("/market-offers")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<List<MarketOffer>> getAllMarketOffers(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of MarketOffers");
        Page<MarketOffer> page = marketOfferRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/market-offers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /market-offers/:id : get the "id" marketOffer.
     *
     * @param id the id of the marketOffer to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the marketOffer, or with status 404 (Not Found)
     */
    @GetMapping("/market-offers/{id}")
    @Secured(AuthoritiesConstants.ADMIN)
    public ResponseEntity<MarketOffer> getMarketOffer(@PathVariable String id) {
        log.debug("REST request to get MarketOffer : {}", id);
        MarketOffer marketOffer = marketOfferRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(marketOffer));
    }

    /**
     * DELETE  /market-offers/:id : delete the "id" marketOffer.
     *
     * @param id the id of the marketOffer to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/market-offers/{id}")
    @Timed
    @Secured(AuthoritiesConstants.USER)
    public ResponseEntity<Void> deleteMarketOffer(@PathVariable String id) {
        log.debug("REST request to delete MarketOffer : {}", id);
        MarketOffer offer = marketOfferRepository.findOne(id);
        if (!offer.getIssuer().equalsIgnoreCase(SecurityUtils.getCurrentUserLogin())) {
            return ResponseEntity.status(403).build();
        }
        marketOfferRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}

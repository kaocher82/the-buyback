package com.thebuyback.eve.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.thebuyback.eve.domain.MarketOffer;

import com.thebuyback.eve.repository.MarketOfferRepository;
import com.thebuyback.eve.web.rest.errors.BadRequestAlertException;
import com.thebuyback.eve.web.rest.util.HeaderUtil;
import com.thebuyback.eve.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<MarketOffer> createMarketOffer(@RequestBody MarketOffer marketOffer) throws URISyntaxException {
        log.debug("REST request to save MarketOffer : {}", marketOffer);
        if (marketOffer.getId() != null) {
            throw new BadRequestAlertException("A new marketOffer cannot already have an ID", ENTITY_NAME, "idexists");
        }
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
    public ResponseEntity<MarketOffer> updateMarketOffer(@RequestBody MarketOffer marketOffer) throws URISyntaxException {
        log.debug("REST request to update MarketOffer : {}", marketOffer);
        if (marketOffer.getId() == null) {
            return createMarketOffer(marketOffer);
        }
        MarketOffer result = marketOfferRepository.save(marketOffer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, marketOffer.getId().toString()))
            .body(result);
    }

    /**
     * GET  /market-offers : get all the marketOffers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of marketOffers in body
     */
    @GetMapping("/market-offers")
    @Timed
    public ResponseEntity<List<MarketOffer>> getAllMarketOffers(Pageable pageable) {
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
    @Timed
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
    public ResponseEntity<Void> deleteMarketOffer(@PathVariable String id) {
        log.debug("REST request to delete MarketOffer : {}", id);
        marketOfferRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}

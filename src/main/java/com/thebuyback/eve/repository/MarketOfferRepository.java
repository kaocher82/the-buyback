package com.thebuyback.eve.repository;

import java.util.List;

import com.thebuyback.eve.domain.MarketOffer;
import com.thebuyback.eve.domain.enumeration.MarketOfferType;

import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the MarketOffer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketOfferRepository extends MongoRepository<MarketOffer, String> {
    List<MarketOffer> findAllByType(MarketOfferType marketOfferType);

    List<MarketOffer> findAllByTypeAndIssuer(MarketOfferType type, String issuer);
}

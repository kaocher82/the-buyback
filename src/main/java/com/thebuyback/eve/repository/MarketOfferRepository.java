package com.thebuyback.eve.repository;

import com.thebuyback.eve.domain.MarketOffer;
import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the MarketOffer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketOfferRepository extends MongoRepository<MarketOffer, String> {

}

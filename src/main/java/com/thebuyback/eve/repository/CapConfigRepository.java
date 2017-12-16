package com.thebuyback.eve.repository;

import com.thebuyback.eve.domain.CapConfig;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the CapConfig entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CapConfigRepository extends MongoRepository<CapConfig, String> {
}

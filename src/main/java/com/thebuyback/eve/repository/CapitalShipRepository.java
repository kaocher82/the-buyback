package com.thebuyback.eve.repository;

import java.util.List;

import com.thebuyback.eve.domain.CapitalShipOnContract;
import com.thebuyback.eve.domain.CapitalShipStatus;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Doctrine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CapitalShipRepository extends MongoRepository<CapitalShipOnContract,String> {
    void deleteByStatus(CapitalShipStatus status);
    List<CapitalShipOnContract> findAllByStatus(CapitalShipStatus publicContract);
}

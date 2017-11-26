package com.thebuyback.eve.repository;

import java.util.List;

import com.thebuyback.eve.domain.CapitalShip;
import com.thebuyback.eve.domain.CapitalShipStatus;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Doctrine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CapitalShipRepository extends MongoRepository<CapitalShip,String> {
    void deleteByStatus(CapitalShipStatus status);
    List<CapitalShip> findAllByStatus(CapitalShipStatus publicContract);
}
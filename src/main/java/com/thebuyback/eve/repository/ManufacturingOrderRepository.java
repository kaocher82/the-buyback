package com.thebuyback.eve.repository;

import java.util.stream.Stream;

import com.thebuyback.eve.domain.ManufacturingOrder;
import com.thebuyback.eve.domain.enumeration.ManufacturingOrderStatus;

import org.springframework.stereotype.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the ManufacturingOrder entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ManufacturingOrderRepository extends MongoRepository<ManufacturingOrder, String> {
    Stream<ManufacturingOrder> findAllByStatus(ManufacturingOrderStatus status);
}

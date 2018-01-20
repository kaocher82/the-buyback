package com.thebuyback.eve.repository;

import java.time.Instant;
import java.util.List;

import com.thebuyback.eve.domain.MarketOrder;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Doctrine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarketOrderRepository extends MongoRepository<MarketOrder,String> {

    void deleteByLocationIdAndTimestampGreaterThan(Long locationId, Instant timestamp);

    void deleteByLocationId(Long locationId);

    List<MarketOrder> findByLocationIdAndIsBuyOrder(Long locationId, Boolean isBuyOrder);

    List<MarketOrder> findByLocationIdAndIsBuyOrderAndTypeIdOrderByPriceAsc(Long locationId, Boolean isBuyOrder, Long typeId);

    List<MarketOrder> findByLocationIdAndIsBuyOrderOrderByPriceAsc(Long locationId, Boolean isBuyOrder);

    List<MarketOrder> findByLocationIdAndIsBuyOrderOrderByPriceDesc(Long locationId, Boolean isBuyOrder);

    List<MarketOrder> findByLocationIdAndIsBuyOrderAndTypeIdOrderByPriceDesc(Long locationId, boolean isBuy, Long typeId);

    MarketOrder findFirstByLocationIdAndIsBuyOrderAndTypeIdOrderByPriceAsc(Long jita, boolean isBuy, int typeId);

    List<MarketOrder> findByLocationIdAndTypeIdInAndIsBuyOrderOrderByPriceAsc(long locationId, List<Long> typeIds, boolean isBuyOrder);
}

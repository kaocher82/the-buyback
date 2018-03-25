package com.thebuyback.eve.repository;

import java.util.List;
import java.util.Set;

import com.thebuyback.eve.domain.ItemBuybackRate;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@SuppressWarnings("unused")
@Repository
public interface ItemBuybackRateRepository extends MongoRepository<ItemBuybackRate, String> {
    List<ItemBuybackRate> findAllByTypeIdIn(Set<Long> typeIds);

    ItemBuybackRate findOneByTypeId(long typeId);
}

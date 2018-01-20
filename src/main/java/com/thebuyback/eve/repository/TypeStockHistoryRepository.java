package com.thebuyback.eve.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.thebuyback.eve.domain.stock.Hub;
import com.thebuyback.eve.domain.stock.TypeStockHistory;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface TypeStockHistoryRepository extends MongoRepository<TypeStockHistory, String> {
    List<TypeStockHistory> findByHubAndTypeId(Hub hub, long typeId);

    Optional<TypeStockHistory> findByDateAndTypeIdAndHub(LocalDate date, Long typeId, Hub hub);
}

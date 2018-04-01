package com.thebuyback.eve.repository;

import java.time.LocalDate;
import java.util.Optional;

import com.thebuyback.eve.domain.Asset;
import com.thebuyback.eve.domain.AssetHistory;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetHistoryRepository extends MongoRepository<AssetHistory, String> {
    Optional<AssetHistory> findOneByDate(final LocalDate date);
}

package com.thebuyback.eve.repository;

import java.time.LocalDate;
import java.util.Optional;

import com.thebuyback.eve.domain.NetWorth;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NetWorthHistoryRepository extends MongoRepository<NetWorth, String> {
    Optional<NetWorth> findOneByDate(final LocalDate date);
}

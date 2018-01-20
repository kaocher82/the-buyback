package com.thebuyback.eve.repository;

import java.util.List;

import com.thebuyback.eve.domain.stock.Fitting;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface FittingRepository extends MongoRepository<Fitting, String> {
    List<Fitting> findAllByIdIn(List<String> ids);
}

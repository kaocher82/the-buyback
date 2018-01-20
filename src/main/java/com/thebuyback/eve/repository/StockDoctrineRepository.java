package com.thebuyback.eve.repository;

import java.util.List;

import com.thebuyback.eve.domain.stock.Hub;
import com.thebuyback.eve.domain.stock.StockDoctrine;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockDoctrineRepository extends MongoRepository<StockDoctrine, String> {
    List<StockDoctrine> findByHub(Hub hub);
}

package com.thebuyback.eve.repository;

import java.util.List;

import com.thebuyback.eve.domain.stock.Doctrine;
import com.thebuyback.eve.domain.stock.Hub;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface DoctrineRepository extends MongoRepository<Doctrine, String> {
    List<Doctrine> findByHub(Hub hub);
}

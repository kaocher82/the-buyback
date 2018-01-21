package com.thebuyback.eve.repository;

import java.util.List;
import java.util.Optional;

import com.thebuyback.eve.domain.stock.Hub;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface HubRepository extends MongoRepository<Hub, String> {
    Optional<Hub> findById(long id);
    List<Hub> findAllByIsPublic(boolean isPublic);
}

package com.thebuyback.eve.repository;

import com.thebuyback.eve.domain.AssetFilter;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetFilterRepository extends MongoRepository<AssetFilter, String> {
}

package com.thebuyback.eve.repository;

import java.util.List;
import java.util.Set;

import com.thebuyback.eve.domain.Asset;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends MongoRepository<Asset, String> {

    List<Asset> findAllByTypeNameInAndLocationNameIn(Set<String> typeNames, List<String> locationNames);

    List<Asset> findAllByLocationName(String locationName);
}

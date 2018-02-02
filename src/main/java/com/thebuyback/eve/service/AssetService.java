package com.thebuyback.eve.service;

import java.util.List;
import java.util.Set;

import com.thebuyback.eve.domain.Asset;
import com.thebuyback.eve.repository.AssetRepository;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class AssetService {

    private final MongoTemplate mongoTemplate;
    private final AssetRepository assetRepository;

    public AssetService(final MongoTemplate mongoTemplate, final AssetRepository assetRepository) {
        this.mongoTemplate = mongoTemplate;
        this.assetRepository = assetRepository;
    }

    public List<Asset> findAssets(Set<String> typeNames) {
        return assetRepository.findAllByTypeNameIn(typeNames);
    }
}

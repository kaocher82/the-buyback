package com.thebuyback.eve.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.thebuyback.eve.domain.Asset;
import com.thebuyback.eve.domain.AssetOverview;
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
        // limit to GE Fort and 68 KS
        return assetRepository.findAllByTypeNameInAndLocationNameIn(typeNames, Arrays.asList("68FT-6 - Mothership Bellicose", "GE-8JV - BROADCAST4REPS"));
    }

    public AssetOverview getAssetsForOverview(final String region, final String isHub) {
        final List<String> systems = new ArrayList<>();
        Long locationId = null;
        if (region.equals("442-CS")) {
            if (isHub.equals("hub")) {
                locationId = 1024389132752L;
            } else {
                systems.addAll(Arrays.asList("442-CS", "9ZFH-Z", "Z-N9IP", "PZMA-E", "TWJ-AW", "4-MPSJ", "N-7ECY"));
            }
        } else if (region.equals("68FT-6")) {
            if (isHub.equals("hub")) {
                locationId = 1023425394442L;
            } else {
                systems.addAll(Arrays.asList("68FT-6", "AFJ-NB", "IV-UNR", "YALR-F", "H-64KI", "9I-SRF", "9-IIBL", "5GQ-S9", "HOHF-B", "Y-6B0E", "IRE-98"));
            }
        } else if (region.equals("TM-0P2")) {
            if (isHub.equals("hub")) {
                locationId = 1L; // todo: get real ID for tm- fort
            } else {
                systems.addAll(Arrays.asList("TM-0P2", "E3-SDZ", "N-CREL", "4OIV-X", "Y-JKJ8"));
            }
        } else if (region.equals("GE-8JV")) {
            if (isHub.equals("hub")) {
                locationId = 1023729674815L;
            } else {
                systems.addAll(Arrays.asList("GE-8JV", "AOK-WQ", "B-XJX4", "7LHB-Z", "E1-Y4H", "MUXX-4", "AX-DOT", "YHN-3K", "V-3YG7", "3-OKDA", "4M-HGL", "MY-W1V", "8B-2YA", "SNFV-I", "HP-64T"));
            }
        }

        final List<Asset> assets;
        if (locationId != null) {
            assets = assetRepository.findAllByLocationId(locationId);
        } else {
            assets = assetRepository.findAll().stream()
                           .filter(asset -> startsWithAnyOf(asset.getLocationName(), systems))
                           .collect(Collectors.toList());
        }

        final double sum = assets.stream()
                                 .filter(asset -> asset.getTypeName() != null && !asset.getTypeName().contains("Blueprint"))
                                 .mapToDouble(asset -> asset.getQuantity() * asset.getPrice())
                                 .sum();

        return new AssetOverview(0, 0, 0, sum);
    }

    boolean startsWithAnyOf(final String locationName, final Iterable<String> systems) {
        for (String system : systems) {
            if (locationName.startsWith(system)) {
                return true;
            }
        }
        return false;
    }
}

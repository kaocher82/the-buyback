package com.thebuyback.eve.web.rest;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.codahale.metrics.annotation.Timed;
import com.thebuyback.eve.domain.Asset;
import com.thebuyback.eve.domain.AssetOverview;
import com.thebuyback.eve.security.SecurityUtils;
import com.thebuyback.eve.service.AssetService;
import com.thebuyback.eve.web.dto.AssetDTO;
import com.thebuyback.eve.web.dto.AssetRequest;
import com.thebuyback.eve.web.dto.AssetsPerSystem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assets")
public class AssetResource {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final AssetService assetService;

    public AssetResource(final AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping
    @Timed
    public ResponseEntity<List<AssetDTO>> requestAssets(@RequestBody AssetRequest appraisal) {
        final Set<String> typeNames = Arrays.stream(appraisal.getText().split("\n"))
                                            .filter(Objects::nonNull)
                                            .map(String::trim)
                                            .filter(line -> !line.isEmpty())
                                            .collect(Collectors.toSet());

        if (typeNames.size() > 50) {
            return ResponseEntity.status(420).build();
        }

        final List<AssetDTO> assets = assetService.findAssets(typeNames)
                                                  .stream()
                                                  .map(AssetResource::toDTO)
                                                  .collect(Collectors.toList());

        log.info("{} searched for {} items: {}", SecurityUtils.getCurrentUserLoginAsString(), typeNames.size(), typeNames.toArray());

        return ResponseEntity.ok(assets);
    }

    @GetMapping("/{region}/{isHub}")
    public ResponseEntity<AssetOverview> getAssetOverview(@PathVariable final String region, @PathVariable final String isHub) {
        return ResponseEntity.ok(assetService.getAssetsForOverview(region, isHub));
    }

    @GetMapping("/{region}/{isHub}/details")
    public ResponseEntity<Set<AssetsPerSystem>> getAssetOverviewDetails(@PathVariable final String region, @PathVariable final String isHub) {
        return ResponseEntity.ok(assetService.getAssetsForOverviewDetails(region, isHub));
    }

    private static final Comparator<AssetDTO> COMPARE_BY_LOCATION
        = (Comparator<AssetDTO>) (o1, o2) -> o1.getLocationName().compareTo(o2.getLocationName());

    private static final Comparator<AssetDTO> COMPARE_BY_NAME
        = (Comparator<AssetDTO>) (o1, o2) -> o1.getTypeName().compareTo(o2.getTypeName());

    private static AssetDTO toDTO(final Asset asset) {
        return new AssetDTO(asset.getTypeName(), asset.getQuantity(), asset.getLocationName(), asset.getLocationFlag());
    }
}

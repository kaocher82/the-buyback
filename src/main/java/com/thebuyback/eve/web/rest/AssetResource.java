package com.thebuyback.eve.web.rest;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.codahale.metrics.annotation.Timed;
import com.thebuyback.eve.domain.Asset;
import com.thebuyback.eve.security.SecurityUtils;
import com.thebuyback.eve.service.AssetService;
import com.thebuyback.eve.web.dto.AssetDTO;
import com.thebuyback.eve.web.dto.AssetRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<AssetDTO>> requestAppraisal(@RequestBody AssetRequest appraisal) {
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
        assets.sort(COMPARE_BY_LOCATION);
        assets.sort(COMPARE_BY_NAME);

        log.info("{} searched for {} items.", SecurityUtils.getCurrentUserLoginAsString(), typeNames.size());

        return ResponseEntity.ok(assets);
    }

    private static final Comparator<AssetDTO> COMPARE_BY_LOCATION
        = (Comparator<AssetDTO>) (o1, o2) -> o1.getLocationName().compareTo(o2.getLocationName());

    private static final Comparator<AssetDTO> COMPARE_BY_NAME
        = (Comparator<AssetDTO>) (o1, o2) -> o1.getTypeName().compareTo(o2.getTypeName());

    private static AssetDTO toDTO(final Asset asset) {
        return new AssetDTO(asset.getTypeName(), asset.getQuantity(), asset.getLocationName());
    }
}

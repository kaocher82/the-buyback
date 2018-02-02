package com.thebuyback.eve.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thebuyback.eve.domain.Asset;
import com.thebuyback.eve.domain.Token;
import com.thebuyback.eve.repository.AssetRepository;
import com.thebuyback.eve.repository.TokenRepository;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Service;

@Service
public class AssetParser implements SchedulingConfigurer {

    private final Logger log = LoggerFactory.getLogger(getClass());

    static final String ASSET_PARSER_CLIENT = "c532e487ed41476e9c546dc2fd8ee56b";

    private final JsonRequestService requestService;
    private final AssetRepository assetRepository;
    private final TypeService typeService;
    private final LocationService locationService;
    private final TokenRepository tokenRepository;

    public AssetParser(final JsonRequestService requestService, final AssetRepository assetRepository,
                       final TypeService typeService,
                       final LocationService locationService,
                       final TokenRepository tokenRepository) {
        this.requestService = requestService;
        this.assetRepository = assetRepository;
        this.typeService = typeService;
        this.locationService = locationService;
        this.tokenRepository = tokenRepository;
    }

    @Async
    public void refreshAssets() {
        final Token token = tokenRepository.findByClientId(ASSET_PARSER_CLIENT).get(0);
        final String accessToken;
        try {
            accessToken = requestService.getAccessToken(token);
        } catch (UnirestException e) {
            log.error("Failed to get access token for refreshAssets.", e);
            return;
        }

        final Collection<Asset> assets = new ArrayList<>();
        boolean nextPageAvailable = true;
        int pageCounter = 0;
        while (nextPageAvailable) {
            final Optional<JsonNode> jsonNode = requestService.getAssets(accessToken, pageCounter);
            if (jsonNode.isPresent()) {
                final JSONArray assetsArray = jsonNode.get().getArray();
                for (int i = 0; i < assetsArray.length(); i++) {
                    final JSONObject asset = assetsArray.getJSONObject(i);
                    final long itemId = asset.getLong("item_id");
                    final long typeId = asset.getLong("type_id");
                    final long quantity = asset.getLong("quantity");
                    final long locationId = asset.getLong("location_id");
                    if (locationId < 100_000_000) {
                        continue;
                    }
                    final String typeName = typeService.getNameByTypeId(typeId);
                    final String locationName = locationService.fetchLocationName(locationId);
                    if (locationName.equals("N/A")) {
                        continue;
                    }
                    assets.add(new Asset(itemId, typeId, typeName, quantity, locationId, locationName));
                }
                if (assetsArray.length() < 1000) {
                    nextPageAvailable = false;
                } else {
                    pageCounter++;
                }
            } else {
                nextPageAvailable = false;
            }

        }
        assetRepository.deleteAll();
        assetRepository.save(assets);
        log.info("Asset parsing complete. {} entries have been added.", assets.size());
    }

    @Bean
    public ThreadPoolTaskScheduler taskExecutor() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.initialize();
        return scheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.setScheduler(taskExecutor());
        taskRegistrar.addTriggerTask(
            this::refreshAssets,
            triggerContext -> Date.from(requestService.getNextExecutionTime("corpAssets")));
    }

}

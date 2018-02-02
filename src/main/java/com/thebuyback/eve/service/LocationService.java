package com.thebuyback.eve.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thebuyback.eve.domain.Token;
import com.thebuyback.eve.repository.TokenRepository;

import static com.thebuyback.eve.service.AssetParser.ASSET_PARSER_CLIENT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final JsonRequestService requestService;
    private final TokenRepository tokenRepository;
    private final Map<Long, String> cache = new HashMap<>();

    public LocationService(final JsonRequestService requestService,
                           final TokenRepository tokenRepository) {
        this.requestService = requestService;
        this.tokenRepository = tokenRepository;
    }

    String fetchLocationName(final long locationId) {
        if (!cache.containsKey(locationId)) {
            setLocationName(locationId);
        }
        return cache.get(locationId);
    }

    private void setLocationName(final long locationId) {
        final Token token = tokenRepository.findByClientId(ASSET_PARSER_CLIENT).get(0);
        final String accessToken;
        String locationName = "N/A";
        try {
            accessToken = requestService.getAccessToken(token);
            final Optional<JsonNode> optional = requestService.getStructureInfo(locationId, accessToken);
            if (optional.isPresent()) {
                locationName = optional.get().getObject().getString("name");
            } else {
                log.warn("Failed to get location infos for {}.", locationId);
            }
        } catch (UnirestException e) {
            log.error("Failed to get access token for fetchLocationName.", e);
        }
        cache.put(locationId, locationName);
        log.info("Added a locationName. Now holding {} locations.", cache.size());
    }
}

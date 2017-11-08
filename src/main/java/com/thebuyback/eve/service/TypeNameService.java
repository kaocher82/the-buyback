package com.thebuyback.eve.service;

import java.util.HashMap;
import java.util.Map;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TypeNameService {

    private final Logger log = LoggerFactory.getLogger(TypeNameService.class);
    private Map<Long, String> typeNames = new HashMap<>();

    public String getTypeName(long typeId) {

        if (!typeNames.containsKey(typeId)) {
            HttpResponse<JsonNode> jsonResponse;
            try {
                jsonResponse = Unirest.get("https://esi.tech.ccp.is/v2/universe/types/" + typeId).asJson();
            } catch (UnirestException e) {
                log.error("Failed to get typeName.", e);
                return "N/A";
            }

            String typeName;
            if (jsonResponse.getStatus() == 200) {
                typeName = jsonResponse.getBody().getObject().getString("name");
            } else {
                log.warn("Failed to load typeInformation for {} from ESI with statusCode {}.", typeId, jsonResponse.getStatus());
                return "N/A";
            }
            typeNames.put(typeId, typeName);
        }

        return typeNames.get(typeId);
    }
}

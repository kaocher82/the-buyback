package com.thebuyback.eve.service;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.BaseRequest;
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.request.body.MultipartBody;
import com.mashape.unirest.request.body.RequestBodyEntity;
import com.thebuyback.eve.domain.Token;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class JsonRequestService {

    private static final String WRONG_STATUS_CODE = "{} returned status code {}.";
    private static final String UNIREST_EXCEPTION = "Failed to get data from url={}";
    private static final String BODY_TEMPLATE = "{\"recipients\": [{\"recipient_type\": \"character\",\"recipient_id\": %d}, {\"recipient_type\": \"corporation\",\"recipient_id\": 98503372}],\"subject\": \"The Buyback - Wrong contract price\",\"body\": \"%s\", \"approved_cost\": 100000}";
    private static final long CORPORATION = 98503372L;
    private static final long MAIL_CHAR = 93475128L;
    private static final String ESI_BASE_URL = "https://esi.tech.ccp.is";
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final Map<String, String> defaultHeaders;
    private final Map<String, Instant> esiCacheExpiries = new HashMap<>();

    private static final DateTimeFormatter EXPIRY_FORMATTER = new DateTimeFormatterBuilder()
        .appendPattern("EEE, dd MMM yyyy HH:mm:ss zzz")
        .toFormatter()
        .withZone(ZoneOffset.UTC);

    public JsonRequestService() {
        defaultHeaders = new HashMap<>();
        defaultHeaders.put("X-User-Agent", "EvE: Rihan Shazih");
        defaultHeaders.put("Accept-Encoding", "gzip");
    }

    public Instant getNextExecutionTime(final String useCase) {
        return esiCacheExpiries.containsKey(useCase) ? esiCacheExpiries.get(useCase) : Instant.now().plusSeconds(60);
    }

    public String getAccessToken(final Token token) throws UnirestException {
        HttpResponse<JsonNode> response = Unirest.post("https://login.eveonline.com/oauth/token")
                                                 .headers(defaultHeaders)
                                                 .field("grant_type","refresh_token")
                                                 .field("refresh_token", token.getRefreshToken())
                                                 .basicAuth(token.getClientId(), token.getClientSecret())
                                                 .asJson();
        final JSONObject object = response.getBody().getObject();
        return object.getString("access_token");
    }

    public Optional<JsonNode> getAccessToken(final String clientId, final String clientSecret, final String code) {
        String url = "https://login.eveonline.com/oauth/token";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        Map<String, Object> fields = new HashMap<>();
        fields.put("grant_type", "authorization_code");
        fields.put("code", code);

        MultipartBody postRequest = post(url, clientId, clientSecret, headers, fields);

        return executeRequest(postRequest);
    }

    public Optional<JsonNode> getUserDetails(final String accessToken) {
        String url = "https://login.eveonline.com/oauth/verify";
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "Bearer " + accessToken);

        GetRequest getRequest = get(url, headers);

        return executeRequest(getRequest);
    }

    private Optional<JsonNode> justGet(final String url, final String useCase) {
        return executeRequest(get(url, null), useCase);
    }

    Optional<JsonNode> executeRequest(final BaseRequest request) {
        return executeRequest(request, null);
    }

    Optional<JsonNode> executeRequest(final BaseRequest request, final String useCase) {
        try {
            HttpResponse<JsonNode> response = request.asJson();
            if (response.getStatus() != 200) {
                log.warn(WRONG_STATUS_CODE, request.getHttpRequest().getUrl(), response.getStatus());
                return Optional.empty();
            }
            if (null != useCase) {
                final String expires = response.getHeaders().getFirst("Expires");
                esiCacheExpiries.put(useCase, parseInstant(expires));
            }
            return Optional.of(response.getBody());
        } catch (UnirestException e) {
            log.error(UNIREST_EXCEPTION, request.getHttpRequest().getUrl(), e);
            return Optional.empty();
        }
    }

    private Instant parseInstant(final CharSequence dateString) {
        return EXPIRY_FORMATTER.parse(dateString, Instant::from);
    }

    GetRequest get(String url, Map<String, String> headers) {
        return Unirest.get(url).headers(defaultHeaders).headers(headers);
    }

    MultipartBody post(final String url, final String username, final String password, final Map<String, String> headers, final Map<String, Object> fields) {
        return Unirest.post(url).basicAuth(username, password).headers(defaultHeaders).headers(headers).fields(fields);
    }

    RequestBodyEntity post(final String url, final String body) {
        return Unirest.post(url).body(body);
    }

    Optional<JsonNode> getCorpContracts(final String accessToken) {
        return justGet(String.format("%s/v1/corporations/%d/contracts/?token=%s", ESI_BASE_URL, CORPORATION, accessToken), "corpContracts");
    }

    Optional<JsonNode> getCorpContractItems(final long contractId, final String accessToken) {
        return justGet(String.format("%s/v1/corporations/%d/contracts/%d/items/?token=%s", ESI_BASE_URL, CORPORATION, contractId, accessToken), "corpContractItems");
    }

    Optional<JsonNode> getCharacterName(final long characterId) {
        return justGet(String.format("%s/v1/characters/names/?character_ids=%d", ESI_BASE_URL, characterId), "characterName");
    }

    public Optional<String> sendMail(final long issuerId, final String mail, final String accessToken) {
        final String body = String.format(BODY_TEMPLATE, issuerId, mail);
        RequestBodyEntity request = post(ESI_BASE_URL + String.format("/v1/characters/%d/mail/?token=%s", MAIL_CHAR, accessToken), body);

        try {
            HttpResponse<String> response = request.asString();
            if (response.getStatus() != 201) {
                log.warn(WRONG_STATUS_CODE, request.getHttpRequest().getUrl(), response.getStatus());
                return Optional.empty();
            }
            return Optional.of(response.getBody());
        } catch (UnirestException e) {
            log.error(UNIREST_EXCEPTION, request.getHttpRequest().getUrl(), e);
            return Optional.empty();
        }
    }
}

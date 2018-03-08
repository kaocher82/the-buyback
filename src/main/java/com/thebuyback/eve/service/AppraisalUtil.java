package com.thebuyback.eve.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.thebuyback.eve.domain.ItemWithQuantity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppraisalUtil {

    private static final Logger LOG = LoggerFactory.getLogger(AppraisalUtil.class);

    private AppraisalUtil() {
    }

    public static String getLinkFromRaw(String raw) throws UnirestException {
        if (raw.isEmpty()) {
            return null;
        }
        LOG.info("Tyring to get evepraisal link for {}.", raw.replace("\n", ";"));
        HttpResponse<String> stringHttpResponse = Unirest.post("http://evepraisal.com/appraisal")
                                                         .field("raw_textarea", raw)
                                                         .field("market", "jita")
                                                         .asString();

        String body = stringHttpResponse.getBody();
        final String[] firstSplit = body.split("Evepraisal - Appraisal Result ");
        if (firstSplit.length == 0) {
            LOG.warn("The following text could not be appraised: {}", raw);
        }
        String listingId = firstSplit[1].split(" ")[0];
        return "http://evepraisal.com/a/" + listingId;
    }

    public static double getBuy(final String appraisalLink) throws UnirestException {
        String url = appraisalLink + ".json";
        HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
        double sell = 0;
        try {
            sell = jsonResponse.getBody().getObject().getJSONObject("totals").getDouble("buy");
        } catch (JSONException e) {
            LOG.warn("an exception occurred", e);
        }
        return sell;
    }

    public static List<ItemWithQuantity> getItems(final String appraisalLink) throws UnirestException {
        List<ItemWithQuantity> result = new ArrayList<>();
        HttpResponse<JsonNode> jsonResponse = Unirest.get(appraisalLink + ".json").asJson();
        JSONObject body = jsonResponse.getBody().getObject();
        JSONArray items = body.getJSONArray("items");
        for (int i = 0; i < items.length(); i++) {
            JSONObject jsonObject = items.getJSONObject(i);
            int typeID = jsonObject.getInt("typeID");
            String typeName = jsonObject.getString("typeName");
            int quantity = jsonObject.getInt("quantity");
            final double jitaBuyPerUnit = getJitaBuyPrice(jsonObject);
            result.add(new ItemWithQuantity(typeName, typeID, quantity, jitaBuyPerUnit));
        }
        return result.stream()
            .sorted(Comparator.comparing(ItemWithQuantity::getTypeName))
            .collect(Collectors.toList());
    }

    private static double getJitaBuyPrice(final JSONObject jsonObject) {
        final JSONObject prices = jsonObject.getJSONObject("prices");
        if (!prices.has("buy")) {
            return 0;
        }
        final JSONObject buyPrices = prices.getJSONObject("buy");
        return buyPrices.has("max") ? buyPrices.getDouble("max") : 0;
    }

    public static double getSell(final String appraisalLink) throws UnirestException {
        String url = appraisalLink + ".json";
        HttpResponse<JsonNode> jsonResponse = Unirest.get(url).asJson();
        double sell = 0;
        try {
            sell = jsonResponse.getBody().getObject().getJSONObject("totals").getDouble("sell");
        } catch (JSONException e) {
            LOG.warn("an exception occurred", e);
        }
        return sell;
    }
}

package com.thebuyback.eve.domain;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Field;

public class Appraisal {

    @Field("raw")
    private String raw;
    @Field("link")
    private String link;
    @Field("additionalRaw")
    private String additionalRaw;
    @Field("jitaBuy")
    private Double jitaBuy;
    @Field("jitaSell")
    private Double jitaSell;
    @Field("items")
    private List<ItemWithQuantity> items;
    @Field("buybackPrice")
    private double buybackPrice;

    public Appraisal() {
    }

    public Appraisal(final String raw) {
        this.raw = raw;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(final String raw) {
        this.raw = raw;
    }

    public String getLink() {
        return link;
    }

    public void setLink(final String link) {
        this.link = link;
    }

    public void updateRaw() {
        if (null != additionalRaw) {
            if (raw == null) {
                raw = additionalRaw;
            } else {
                raw += "\n" + additionalRaw;
            }
            additionalRaw = null;
        }
    }

    public void setAdditionalRaw(final String additionalRaw) {
        this.additionalRaw = additionalRaw;
    }

    public String getAdditionalRaw() {
        return additionalRaw;
    }

    public Double getJitaBuy() {
        return jitaBuy;
    }

    public void setJitaBuy(final Double jitaBuy) {
        this.jitaBuy = jitaBuy;
    }

    public List<ItemWithQuantity> getItems() {
        return items;
    }

    public void setItems(final List<ItemWithQuantity> items) {
        this.items = items;
    }

    public Double getJitaSell() {
        return jitaSell;
    }

    public void setJitaSell(final Double jitaSell) {
        this.jitaSell = jitaSell;
    }

    @Override
    public String toString() {
        return "Appraisal{" +
               "raw='" + raw + '\'' +
               ", link='" + link + '\'' +
               ", additionalRaw='" + additionalRaw + '\'' +
               ", jitaBuy='" + jitaBuy + '\'' +
               '}';
    }

    public void setBuybackPrice(final double buybackPrice) {
        this.buybackPrice = buybackPrice;
    }

    public double getBuybackPrice() {
        return buybackPrice;
    }
}

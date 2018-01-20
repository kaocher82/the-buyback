package com.thebuyback.eve.domain;

import java.time.Instant;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "marketOrder")
public class MarketOrder {

    private Long orderId;
    private Long typeId;
    private Long locationId;
    private Long volumeTotal;
    private Long volumeRemain;
    private Long minVolume;
    private Double price;
    private Boolean isBuyOrder;
    private Integer duration;
    private Instant issued;
    private String range;
    private Instant timestamp;

    public MarketOrder() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(final Long orderId) {
        this.orderId = orderId;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(final Long typeId) {
        this.typeId = typeId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(final Long locationId) {
        this.locationId = locationId;
    }

    public Long getVolumeTotal() {
        return volumeTotal;
    }

    public void setVolumeTotal(final Long volumeTotal) {
        this.volumeTotal = volumeTotal;
    }

    public Long getVolumeRemain() {
        return volumeRemain;
    }

    public void setVolumeRemain(final Long volumeRemain) {
        this.volumeRemain = volumeRemain;
    }

    public Long getMinVolume() {
        return minVolume;
    }

    public void setMinVolume(final Long minVolume) {
        this.minVolume = minVolume;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Boolean isBuyOrder() {
        return isBuyOrder;
    }

    public void setBuyOrder(final Boolean buyOrder) {
        isBuyOrder = buyOrder;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(final Integer duration) {
        this.duration = duration;
    }

    public Instant getIssued() {
        return issued;
    }

    public void setIssued(final Instant issued) {
        this.issued = issued;
    }

    public String getRange() {
        return range;
    }

    public void setRange(final String range) {
        this.range = range;
    }

    public void setTimestamp(final Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "EsiMarketOrder{" +
               "orderId=" + orderId +
               ", typeId=" + typeId +
               ", locationId=" + locationId +
               ", volumeTotal=" + volumeTotal +
               ", volumeRemain=" + volumeRemain +
               ", minVolume=" + minVolume +
               ", price=" + price +
               ", isBuyOrder=" + isBuyOrder +
               ", duration=" + duration +
               ", issued=" + issued +
               ", range='" + range + '\'' +
               ", timestamp=" + timestamp +
               '}';
    }
}

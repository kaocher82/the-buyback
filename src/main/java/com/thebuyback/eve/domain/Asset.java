package com.thebuyback.eve.domain;

import org.springframework.data.annotation.Id;

public class Asset {

    @Id
    private final long itemId;
    private final long typeId;
    private final long quantity;
    private final long locationId;
    private final String locationFlag;
    private String typeName;
    private String locationName;
    private Double price;
    private Double volume;

    public Asset(final long itemId, final long typeId, final long quantity,
                 final long locationId, final String locationFlag) {
        this.itemId = itemId;
        this.typeId = typeId;
        this.quantity = quantity;
        this.locationId = locationId;
        this.locationFlag = locationFlag;
    }

    public String getLocationFlag() {
        return locationFlag;
    }

    public Long getItemId() {
        return itemId;
    }

    public long getTypeId() {
        return typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public long getQuantity() {
        return quantity;
    }

    public long getLocationId() {
        return locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }

    public void setLocationName(final String locationName) {
        this.locationName = locationName;
    }

    public void setVolume(final double volume) {
        this.volume = volume;
    }

    public Double getVolume() {
        return volume;
    }

    public void setVolume(final Double volume) {
        this.volume = volume;
    }
}

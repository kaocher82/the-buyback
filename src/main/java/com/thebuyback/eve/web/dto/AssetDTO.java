package com.thebuyback.eve.web.dto;

public class AssetDTO {
    private final String typeName;
    private final long quantity;
    private final String locationName;

    public AssetDTO(final String typeName, final long quantity, final String locationName) {
        this.typeName = typeName;
        this.quantity = quantity;
        this.locationName = locationName;
    }

    public String getTypeName() {
        return typeName;
    }

    public long getQuantity() {
        return quantity;
    }

    public String getLocationName() {
        return locationName;
    }
}

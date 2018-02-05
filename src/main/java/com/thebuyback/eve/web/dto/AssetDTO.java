package com.thebuyback.eve.web.dto;

public class AssetDTO {
    private final String typeName;
    private final long quantity;
    private final String locationName;
    private final String locationFlag;

    public AssetDTO(final String typeName, final long quantity, final String locationName, final String locationFlag) {
        this.typeName = typeName;
        this.quantity = quantity;
        this.locationName = locationName;
        this.locationFlag = locationFlag;
    }

    public String getLocationFlag() {
        return locationFlag;
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

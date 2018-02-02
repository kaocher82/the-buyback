package com.thebuyback.eve.domain;

import org.springframework.data.annotation.Id;

public class Asset {

    @Id
    private final long itemId;
    private final long typeId;
    private final String typeName;
    private final long quantity;
    private final long locationId;
    private final String locationName;

    public Asset(final long itemId, final long typeId, final String typeName, final long quantity,
                 final long locationId, final String locationName) {
        this.itemId = itemId;
        this.typeId = typeId;
        this.typeName = typeName;
        this.quantity = quantity;
        this.locationId = locationId;
        this.locationName = locationName;
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
}

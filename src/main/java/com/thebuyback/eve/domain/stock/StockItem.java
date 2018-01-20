package com.thebuyback.eve.domain.stock;

import com.thebuyback.eve.domain.enumeration.Availability;

public class StockItem {
    private long typeId;
    private String typeName;
    private Availability availability;
    private long stocked;
    private long targetStock;

    public StockItem(final long typeId, final String typeName, final Availability availability, final long stocked, final long targetStock) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.availability = availability;
        this.stocked = stocked;
        this.targetStock = targetStock;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(final long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(final Availability availability) {
        this.availability = availability;
    }

    public long getStocked() {
        return stocked;
    }

    public void setStocked(final long stocked) {
        this.stocked = stocked;
    }

    public long getTargetStock() {
        return targetStock;
    }

    public void setTargetStock(final long targetStock) {
        this.targetStock = targetStock;
    }
}

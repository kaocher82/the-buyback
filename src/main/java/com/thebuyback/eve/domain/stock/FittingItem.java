package com.thebuyback.eve.domain.stock;

public class FittingItem {
    private long typeId;
    private long quantity;

    public FittingItem(final long typeId, final long quantity) {
        this.typeId = typeId;
        this.quantity = quantity;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(final long typeId) {
        this.typeId = typeId;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(final long quantity) {
        this.quantity = quantity;
    }
}

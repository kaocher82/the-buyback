package com.thebuyback.eve.domain;

public class ItemWithQuantity {
    private String typeName;
    private long typeID;
    private Integer quantity;
    private double jitaBuyPerUnit;
    private Double rate;

    public ItemWithQuantity() {
    }

    public ItemWithQuantity(final String typeName, final int typeID, final int quantity, final double jitaBuyPerUnit) {
        this.typeName = typeName;
        this.typeID = typeID;
        this.quantity = quantity;
        this.jitaBuyPerUnit = jitaBuyPerUnit;
    }

    public double getJitaBuyPerUnit() {
        return jitaBuyPerUnit;
    }

    public void setJitaBuyPerUnit(final double jitaBuyPerUnit) {
        this.jitaBuyPerUnit = jitaBuyPerUnit;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(final double rate) {
        this.rate = rate;
    }

    public long getTypeID() {
        return typeID;
    }

    public void setTypeID(final long typeID) {
        this.typeID = typeID;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }
}

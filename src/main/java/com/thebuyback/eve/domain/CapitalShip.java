package com.thebuyback.eve.domain;

public class CapitalShip {
    private CapitalShipStatus status;
    private double price;
    private int typeId;
    private String typeName;

    public CapitalShip() {
    }

    public CapitalShip(final CapitalShipStatus status, final double price, final int typeId, final String typeName) {
        this.status = status;
        this.price = price;
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public CapitalShipStatus getStatus() {
        return status;
    }

    public void setStatus(final CapitalShipStatus status) {
        this.status = status;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(final int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }
}

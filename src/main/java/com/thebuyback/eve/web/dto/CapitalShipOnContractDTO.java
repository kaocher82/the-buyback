package com.thebuyback.eve.web.dto;

public class CapitalShipOnContractDTO {
    private int quantity;
    private int typeId;
    private String typeName;

    public CapitalShipOnContractDTO() {
    }

    public CapitalShipOnContractDTO(final int quantity, final String typeName, final int typeId) {
        this.quantity = quantity;
        this.typeName = typeName;
        this.typeId = typeId;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(final int typeId) {
        this.typeId = typeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }
}

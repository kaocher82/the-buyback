package com.thebuyback.eve.domain;

import java.util.List;

import com.thebuyback.eve.domain.stock.TypeStockHistory;

public class DoctrineItemDetails {
    private Long typeId;
    private String typeName;
    private Long stocked;
    private Double priceBorder;
    private Double jitaBuy;
    private Double jitaSell;
    private Double sellPrice;
    private List<TypeStockHistory> stockHistory;

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(final Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Double getPriceBorder() {
        return priceBorder;
    }

    public void setPriceBorder(final Double priceBorder) {
        this.priceBorder = priceBorder;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(final Long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }

    public Long getStocked() {
        return stocked;
    }

    public void setStocked(final Long stocked) {
        this.stocked = stocked;
    }

    public Double getJitaBuy() {
        return jitaBuy;
    }

    public void setJitaBuy(final Double jitaBuy) {
        this.jitaBuy = jitaBuy;
    }

    public Double getJitaSell() {
        return jitaSell;
    }

    public void setJitaSell(final Double jitaSell) {
        this.jitaSell = jitaSell;
    }

    public List<TypeStockHistory> getStockHistory() {
        return stockHistory;
    }

    public void setStockHistory(final List<TypeStockHistory> stockHistory) {
        this.stockHistory = stockHistory;
    }

}

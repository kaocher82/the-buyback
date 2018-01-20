package com.thebuyback.eve.domain.stock;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

public class TypeStockHistory {
    @Id
    private String id;
    private long typeId;
    private Hub hub;
    private LocalDate date;
    private Long maxQuantity;
    private Long minQuantity;
    private Double maxPrice;
    private Double minPrice;

    public TypeStockHistory() {
    }

    public TypeStockHistory(final long typeId, final Hub hub) {
        date = LocalDate.now();
        this.typeId = typeId;
        this.hub = hub;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(final long typeId) {
        this.typeId = typeId;
    }

    public Hub getHub() {
        return hub;
    }

    public void setHub(final Hub hub) {
        this.hub = hub;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public Long getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(final Long maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public Long getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(final Long minQuantity) {
        this.minQuantity = minQuantity;
    }

    public void setMaxPrice(final Double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setMinPrice(final Double minPrice) {
        this.minPrice = minPrice;
    }
}

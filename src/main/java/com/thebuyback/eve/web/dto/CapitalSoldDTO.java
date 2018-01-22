package com.thebuyback.eve.web.dto;

import java.time.Instant;

public class CapitalSoldDTO {
    private long id;
    private Instant date;
    private String hullType;
    private Double price;

    public CapitalSoldDTO(final long id, final Instant date, final String hullType, final Double price) {
        this.id = id;
        this.date = date;
        this.hullType = hullType;
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
        this.price = price;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(final Instant date) {
        this.date = date;
    }

    public String getHullType() {
        return hullType;
    }

    public void setHullType(final String hullType) {
        this.hullType = hullType;
    }
}

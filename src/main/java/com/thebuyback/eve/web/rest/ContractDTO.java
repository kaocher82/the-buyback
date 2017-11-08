package com.thebuyback.eve.web.rest;

import java.time.Instant;

/**
 * ContractDTO
 *
 * Created on 08.11.2017
 *
 */
public class ContractDTO {
    private long id;
    private String client;
    private Instant created;
    private double price;
    private double jitaBuy;

    public ContractDTO() {
    }

    public ContractDTO(final long id, final String client, final Instant created, final double price,
                       final double jitaBuy) {
        this.id = id;
        this.client = client;
        this.created = created;
        this.price = price;
        this.jitaBuy = jitaBuy;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(final String client) {
        this.client = client;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(final Instant created) {
        this.created = created;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public double getJitaBuy() {
        return jitaBuy;
    }

    public void setJitaBuy(final double jitaBuy) {
        this.jitaBuy = jitaBuy;
    }
}

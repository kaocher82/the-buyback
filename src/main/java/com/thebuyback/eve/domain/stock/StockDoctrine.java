package com.thebuyback.eve.domain.stock;

import java.util.List;

import com.thebuyback.eve.domain.enumeration.Availability;

import org.springframework.data.annotation.Id;

public class StockDoctrine {
    @Id
    private String id;
    private Availability availability;
    private String doctrineName;
    private Hub hub;
    private List<StockItem> stockItems;

    public StockDoctrine() {
    }

    public StockDoctrine(final String id, final Availability availability, final String doctrineName, final Hub hub) {
        this.id = id;
        this.availability = availability;
        this.doctrineName = doctrineName;
        this.hub = hub;
    }

    public StockDoctrine(final Availability availability, final String doctrineName, final Hub hub,
                         final List<StockItem> stockItems) {
        this.availability = availability;
        this.doctrineName = doctrineName;
        this.hub = hub;
        this.stockItems = stockItems;
        id = hub.getId() + doctrineName;
    }

    public List<StockItem> getStockItems() {
        return stockItems;
    }

    public void setStockItems(final List<StockItem> stockItems) {
        this.stockItems = stockItems;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(final Availability availability) {
        this.availability = availability;
    }

    public String getDoctrineName() {
        return doctrineName;
    }

    public void setDoctrineName(final String doctrineName) {
        this.doctrineName = doctrineName;
    }

    public Hub getHub() {
        return hub;
    }

    public void setHub(final Hub hub) {
        this.hub = hub;
    }
}

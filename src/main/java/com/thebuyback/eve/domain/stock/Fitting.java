package com.thebuyback.eve.domain.stock;

import java.util.List;

import org.springframework.data.annotation.Id;

public class Fitting {
    @Id
    private String id;
    private String name;
    private List<FittingItem> items;

    public Fitting(final String id, final String name, final List<FittingItem> items) {
        this.id = id;
        this.name = name;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public List<FittingItem> getItems() {
        return items;
    }

    public void setItems(final List<FittingItem> items) {
        this.items = items;
    }
}

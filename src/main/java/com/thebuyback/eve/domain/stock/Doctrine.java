package com.thebuyback.eve.domain.stock;

import java.util.Map;

public class Doctrine {
    private String name;
    private Hub hub;
    private Map<String, Integer> fittingsQuantities;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Hub getHub() {
        return hub;
    }

    public void setHub(final Hub hub) {
        this.hub = hub;
    }

    public Map<String, Integer> getFittingsQuantities() {
        return fittingsQuantities;
    }

    public void setFittingsQuantities(final Map<String, Integer> fittingsQuantities) {
        this.fittingsQuantities = fittingsQuantities;
    }
}

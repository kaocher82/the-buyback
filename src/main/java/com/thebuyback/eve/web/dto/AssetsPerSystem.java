package com.thebuyback.eve.web.dto;

import java.util.Objects;

public class AssetsPerSystem {
    private final String systemName;
    private double stuffWorth;
    private double stuffVolume;

    public AssetsPerSystem(final String systemName, final double stuffWorth, final double stuffVolume) {
        this.systemName = systemName;
        this.stuffWorth = stuffWorth;
        this.stuffVolume = stuffVolume;
    }

    public String getSystemName() {
        return systemName;
    }

    public double getStuffWorth() {
        return stuffWorth;
    }

    public void setStuffWorth(final double stuffWorth) {
        this.stuffWorth = stuffWorth;
    }

    public void addStuffWorth(final double stuffWorth) {
        this.stuffWorth += stuffWorth;
    }

    public double getStuffVolume() {
        return stuffVolume;
    }

    public void setStuffVolume(final double stuffVolume) {
        this.stuffVolume = stuffVolume;
    }

    public void addStuffVolume(final double stuffVolume) {
        this.stuffVolume += stuffVolume;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AssetsPerSystem that = (AssetsPerSystem) o;
        return Objects.equals(systemName, that.systemName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(systemName);
    }
}

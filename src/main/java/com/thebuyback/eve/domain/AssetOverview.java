package com.thebuyback.eve.domain;

public class AssetOverview {
    private final double oreVolume;
    private final double oreValue;
    private final double stuffVolume;
    private final double stuffValue;

    public AssetOverview(final double oreVolume, final double oreValue, final double stuffVolume,
                         final double stuffValue) {
        this.oreVolume = oreVolume;
        this.oreValue = oreValue;
        this.stuffVolume = stuffVolume;
        this.stuffValue = stuffValue;
    }

    public double getOreVolume() {
        return oreVolume;
    }

    public double getOreValue() {
        return oreValue;
    }

    public double getStuffVolume() {
        return stuffVolume;
    }

    public double getStuffValue() {
        return stuffValue;
    }
}

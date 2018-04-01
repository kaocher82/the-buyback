package com.thebuyback.eve.domain;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

public class AssetHistory {
    @Id
    private final LocalDate date;
    private double low;
    private double high;

    public AssetHistory(final LocalDate date, final double low, final double high) {
        this.date = date;
        this.low = low;
        this.high = high;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getLow() {
        return low;
    }

    public void setLow(final double low) {
        this.low = low;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(final double high) {
        this.high = high;
    }
}

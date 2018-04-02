package com.thebuyback.eve.domain;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

public class NetWorth {
    @Id
    private LocalDate date;
    private Double assetLow;
    private Double assetHigh;
    private Double walletLow;
    private Double walletHigh;

    public NetWorth() {
    }

    public NetWorth(final LocalDate date) {
        this.date = date;
    }

    public NetWorth(final LocalDate date, final double assetLow, final double assetHigh) {
        this.date = date;
        this.assetLow = assetLow;
        this.assetHigh = assetHigh;
    }

    public NetWorth(final LocalDate date, final double assetLow, final double assetHigh, final double walletLow,
                    final double walletHigh) {
        this.date = date;
        this.assetLow = assetLow;
        this.assetHigh = assetHigh;
        this.walletLow = walletLow;
        this.walletHigh = walletHigh;
    }

    public LocalDate getDate() {
        return date;
    }

    public Double getAssetLow() {
        return assetLow;
    }

    public void setAssetLow(final Double assetLow) {
        this.assetLow = assetLow;
    }

    public Double getAssetHigh() {
        return assetHigh;
    }

    public void setAssetHigh(final Double assetHigh) {
        this.assetHigh = assetHigh;
    }

    public Double getWalletLow() {
        return walletLow;
    }

    public void setWalletLow(final Double walletLow) {
        this.walletLow = walletLow;
    }

    public Double getWalletHigh() {
        return walletHigh;
    }

    public void setWalletHigh(final Double walletHigh) {
        this.walletHigh = walletHigh;
    }
}

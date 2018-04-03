package com.thebuyback.eve.domain;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;

public class NetWorth {
    @Id
    private LocalDate date;
    private Double assetLow;
    private Double assetHigh;
    private Double assetLatest;
    private Double walletLow;
    private Double walletHigh;
    private Double walletLatest;
    private Double compressedOreLow;
    private Double compressedOreHigh;
    private Double compressedOreLatest;
    private Double marketOrdersLow;
    private Double marketOrdersHigh;
    private Double marketOrdersLatest;

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

    public Double getAssetLatest() {
        return assetLatest;
    }

    public void setAssetLatest(final Double assetLatest) {
        this.assetLatest = assetLatest;
    }

    public Double getWalletLatest() {
        return walletLatest;
    }

    public void setWalletLatest(final Double walletLatest) {
        this.walletLatest = walletLatest;
    }

    public Double getCompressedOreLatest() {
        return compressedOreLatest;
    }

    public void setCompressedOreLatest(final Double compressedOreLatest) {
        this.compressedOreLatest = compressedOreLatest;
    }

    public Double getMarketOrdersLatest() {
        return marketOrdersLatest;
    }

    public void setMarketOrdersLatest(final Double marketOrdersLatest) {
        this.marketOrdersLatest = marketOrdersLatest;
    }

    public Double getMarketOrdersLow() {
        return marketOrdersLow;
    }

    public void setMarketOrdersLow(final Double marketOrdersLow) {
        this.marketOrdersLow = marketOrdersLow;
    }

    public Double getMarketOrdersHigh() {
        return marketOrdersHigh;
    }

    public void setMarketOrdersHigh(final Double marketOrdersHigh) {
        this.marketOrdersHigh = marketOrdersHigh;
    }

    public Double getCompressedOreLow() {
        return compressedOreLow;
    }

    public void setDate(final LocalDate date) {
        this.date = date;
    }

    public void setCompressedOreLow(final Double compressedOreLow) {
        this.compressedOreLow = compressedOreLow;
    }

    public Double getCompressedOreHigh() {
        return compressedOreHigh;
    }

    public void setCompressedOreHigh(final Double compressedOreHigh) {
        this.compressedOreHigh = compressedOreHigh;
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

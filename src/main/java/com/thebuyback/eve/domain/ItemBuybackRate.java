package com.thebuyback.eve.domain;

public class ItemBuybackRate {
    private long typeId;
    private long groupId;
    private double rate;

    public ItemBuybackRate() {
    }

    public ItemBuybackRate(final long typeId, final long groupId, final double rate) {
        this.typeId = typeId;
        this.groupId = groupId;
        this.rate = rate;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(final long typeId) {
        this.typeId = typeId;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(final long groupId) {
        this.groupId = groupId;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(final double rate) {
        this.rate = rate;
    }
}

package com.thebuyback.eve.domain;

import org.springframework.data.annotation.Id;

public class AssetFilter {
    @Id
    private final long typeId;

    public AssetFilter(final long typeId) {
        this.typeId = typeId;
    }

    public long getTypeId() {
        return typeId;
    }
}

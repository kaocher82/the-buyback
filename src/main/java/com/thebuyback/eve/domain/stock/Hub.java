package com.thebuyback.eve.domain.stock;

import org.springframework.data.annotation.Id;

public class Hub {
    @Id
    private long id;
    private String structureType;
    private String systemName;
    private boolean isPublic;

    public Hub() {
    }

    public Hub(final long id, final String structureType, final String systemName, final boolean isPublic) {
        this.structureType = structureType;
        this.id = id;
        this.systemName = systemName;
        this.isPublic = isPublic;
    }

    public String getStructureType() {
        return structureType;
    }

    public void setStructureType(final String structureType) {
        this.structureType = structureType;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(final String systemName) {
        this.systemName = systemName;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(final boolean isPublic) {
        this.isPublic = isPublic;
    }
}

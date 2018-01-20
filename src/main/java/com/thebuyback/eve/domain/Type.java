package com.thebuyback.eve.domain;

import java.util.Objects;

import org.springframework.data.annotation.Id;

public class Type {

    @Id
    private long typeId;
    private String typeName;
    private long groupId;
    private String groupName;
    private Long categoryId;
    private String categoryName;
    private double volume;
    private Double packagedVolume;

    public Type(final long typeId, final String typeName, final long groupId, final String groupName,
                final Long categoryId,
                final String categoryName, final double volume, final Double packagedVolume) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.groupId = groupId;
        this.groupName = groupName;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.volume = volume;
        this.packagedVolume = packagedVolume;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(final long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(final String typeName) {
        this.typeName = typeName;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(final long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(final String groupName) {
        this.groupName = groupName;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(final long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(final String categoryName) {
        this.categoryName = categoryName;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(final double volume) {
        this.volume = volume;
    }

    public Double getPackagedVolume() {
        return packagedVolume;
    }

    public void setPackagedVolume(final double packagedVolume) {
        this.packagedVolume = packagedVolume;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !Objects.equals(getClass(), o.getClass())) {
            return false;
        }
        final Type type = (Type) o;
        return typeId == type.typeId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(typeId);
    }
}

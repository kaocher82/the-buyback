package com.thebuyback.eve.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

import com.thebuyback.eve.domain.enumeration.MarketOfferType;

import com.thebuyback.eve.domain.enumeration.MarketOfferCategory;

/**
 * A MarketOffer.
 */
@Document(collection = "market_offer")
public class MarketOffer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Field("issuer")
    private String issuer;

    @Field("created")
    private Instant created;

    @Field("expiry")
    private Instant expiry;

    @Field("expiry_updated")
    private Instant expiryUpdated;

    @Field("type")
    private MarketOfferType type;

    @Field("category")
    private MarketOfferCategory category;

    @Field("location")
    private String location;

    @Field("is_recurring")
    private Boolean isRecurring;

    @Field("text")
    private String text;

    // jhipster-needle-entity-add-field - Jhipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssuer() {
        return issuer;
    }

    public MarketOffer issuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public Instant getCreated() {
        return created;
    }

    public MarketOffer created(Instant created) {
        this.created = created;
        return this;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getExpiry() {
        return expiry;
    }

    public MarketOffer expiry(Instant expiry) {
        this.expiry = expiry;
        return this;
    }

    public void setExpiry(Instant expiry) {
        this.expiry = expiry;
    }

    public Instant getExpiryUpdated() {
        return expiryUpdated;
    }

    public MarketOffer expiryUpdated(Instant expiryUpdated) {
        this.expiryUpdated = expiryUpdated;
        return this;
    }

    public void setExpiryUpdated(Instant expiryUpdated) {
        this.expiryUpdated = expiryUpdated;
    }

    public MarketOfferType getType() {
        return type;
    }

    public MarketOffer type(MarketOfferType type) {
        this.type = type;
        return this;
    }

    public void setType(MarketOfferType type) {
        this.type = type;
    }

    public MarketOfferCategory getCategory() {
        return category;
    }

    public MarketOffer category(MarketOfferCategory category) {
        this.category = category;
        return this;
    }

    public void setCategory(MarketOfferCategory category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public MarketOffer location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean isIsRecurring() {
        return isRecurring;
    }

    public MarketOffer isRecurring(Boolean isRecurring) {
        this.isRecurring = isRecurring;
        return this;
    }

    public void setIsRecurring(Boolean isRecurring) {
        this.isRecurring = isRecurring;
    }

    public String getText() {
        return text;
    }

    public MarketOffer text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }
    // jhipster-needle-entity-add-getters-setters - Jhipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MarketOffer marketOffer = (MarketOffer) o;
        if (marketOffer.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), marketOffer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "MarketOffer{" +
            "id=" + getId() +
            ", issuer='" + getIssuer() + "'" +
            ", created='" + getCreated() + "'" +
            ", expiry='" + getExpiry() + "'" +
            ", expiryUpdated='" + getExpiryUpdated() + "'" +
            ", type='" + getType() + "'" +
            ", category='" + getCategory() + "'" +
            ", location='" + getLocation() + "'" +
            ", isRecurring='" + isIsRecurring() + "'" +
            ", text='" + getText() + "'" +
            "}";
    }
}

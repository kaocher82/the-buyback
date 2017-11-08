package com.thebuyback.eve.domain;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "token")
public class Token {
    private String clientId;
    private String clientSecret;
    private String refreshToken;

    public Token() {
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(final String refreshToken) {
        this.refreshToken = refreshToken;
    }
}

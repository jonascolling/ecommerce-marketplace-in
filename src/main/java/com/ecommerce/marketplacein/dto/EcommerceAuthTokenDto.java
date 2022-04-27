package com.ecommerce.marketplacein.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EcommerceAuthTokenDto {

    @JsonAlias("access_token")
    @JsonProperty("access_token")
    private String accessToken;

    @JsonAlias("token_type")
    @JsonProperty("token_type")
    private String tokenType;

    @JsonAlias("expires_in")
    @JsonProperty("expires_in")
    private Integer expiresIn;

    @JsonAlias("scope")
    @JsonProperty("scope")
    private String scope;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Integer getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Integer expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }
}

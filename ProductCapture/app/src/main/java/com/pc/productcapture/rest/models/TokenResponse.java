package com.pc.productcapture.rest.models;

public class TokenResponse {
    public final String token;

    public TokenResponse(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "Token='" + token + '\'' +
                '}';
    }
}

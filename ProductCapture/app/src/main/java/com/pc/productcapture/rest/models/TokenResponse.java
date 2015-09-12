package com.pc.productcapture.rest.models;

public class TokenResponse {
    public final String Token;

    public TokenResponse(String token) {
        Token = token;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "Token='" + Token + '\'' +
                '}';
    }
}

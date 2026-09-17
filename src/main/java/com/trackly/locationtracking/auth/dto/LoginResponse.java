package com.trackly.locationtracking.auth.dto;

public record LoginResponse(
        String token,
        String tokenType,
        long expiresInMs
) {
}

package com.trackly.locationtracking.location.dto;

public record LocationPingResponse(
        Long id,
        Double latitude,
        Double longitude,
        Float accuracy,
        long timestamp
) {
}

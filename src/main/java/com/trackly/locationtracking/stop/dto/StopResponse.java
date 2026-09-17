package com.trackly.locationtracking.stop.dto;

import java.time.Instant;

public record StopResponse(
        Long id,
        Long employeeId,
        Double latitude,
        Double longitude,
        String address,
        Instant arrivalTime,
        Instant departureTime,
        Integer durationMinutes
) {
}

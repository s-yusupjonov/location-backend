package com.trackly.locationtracking.location.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LocationPingRequest(
        @NotBlank String deviceId,
        @NotNull Double latitude,
        @NotNull Double longitude,
        Float accuracy,
        @NotNull Long timestamp
) {
}

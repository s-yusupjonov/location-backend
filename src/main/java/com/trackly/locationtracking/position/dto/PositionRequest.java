package com.trackly.locationtracking.position.dto;

import jakarta.validation.constraints.NotBlank;

public record PositionRequest(
        @NotBlank String name
) {
}

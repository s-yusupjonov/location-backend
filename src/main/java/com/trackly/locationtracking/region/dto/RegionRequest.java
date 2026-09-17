package com.trackly.locationtracking.region.dto;

import jakarta.validation.constraints.NotBlank;

public record RegionRequest(
        @NotBlank String name
) {
}

package com.trackly.locationtracking.district.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DistrictRequest(
        @NotBlank String name,
        @NotNull Long regionId
) {
}

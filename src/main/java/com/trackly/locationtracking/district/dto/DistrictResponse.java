package com.trackly.locationtracking.district.dto;

public record DistrictResponse(
        Long id,
        String name,
        Long regionId,
        String regionName
) {
}

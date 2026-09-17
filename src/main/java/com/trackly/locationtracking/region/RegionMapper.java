package com.trackly.locationtracking.region;

import com.trackly.locationtracking.region.dto.RegionResponse;
import org.springframework.stereotype.Component;

@Component
public class RegionMapper {

    public RegionResponse toResponse(Region region) {
        return new RegionResponse(region.getId(), region.getName());
    }
}

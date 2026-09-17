package com.trackly.locationtracking.district;

import com.trackly.locationtracking.district.dto.DistrictResponse;
import org.springframework.stereotype.Component;

@Component
public class DistrictMapper {

    public DistrictResponse toResponse(District district) {
        return new DistrictResponse(
                district.getId(),
                district.getName(),
                district.getRegion().getId(),
                district.getRegion().getName()
        );
    }
}

package com.trackly.locationtracking.location;

import com.trackly.locationtracking.location.dto.LocationPingResponse;
import org.springframework.stereotype.Component;

@Component
public class LocationPingMapper {

    public LocationPingResponse toResponse(LocationPing locationPing) {
        return new LocationPingResponse(
                locationPing.getId(),
                locationPing.getLatitude(),
                locationPing.getLongitude(),
                locationPing.getAccuracy(),
                locationPing.getRecordedAt().toEpochMilli()
        );
    }
}

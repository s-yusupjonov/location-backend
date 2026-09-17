package com.trackly.locationtracking.stop;

import com.trackly.locationtracking.stop.dto.StopResponse;
import org.springframework.stereotype.Component;

@Component
public class StopMapper {

    public StopResponse toResponse(Stop stop) {
        return new StopResponse(
                stop.getId(),
                stop.getEmployee().getId(),
                stop.getLatitude(),
                stop.getLongitude(),
                stop.getAddress(),
                stop.getArrivalTime(),
                stop.getDepartureTime(),
                stop.getDurationMinutes()
        );
    }
}

package com.trackly.locationtracking.position;

import com.trackly.locationtracking.position.dto.PositionResponse;
import org.springframework.stereotype.Component;

@Component
public class PositionMapper {

    public PositionResponse toResponse(Position position) {
        return new PositionResponse(position.getId(), position.getName());
    }
}

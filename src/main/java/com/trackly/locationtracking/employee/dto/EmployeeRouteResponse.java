package com.trackly.locationtracking.employee.dto;

import com.trackly.locationtracking.location.dto.LocationPingResponse;

import java.time.LocalDate;
import java.util.List;

public record EmployeeRouteResponse(
        Long employeeId,
        LocalDate date,
        List<LocationPingResponse> points
) {
}

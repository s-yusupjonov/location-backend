package com.trackly.locationtracking.employee.dto;

import java.time.Instant;

public record EmployeeResponse(
        Long id,
        String firstName,
        String lastName,
        String phoneNumber,
        Long regionId,
        String regionName,
        Long districtId,
        String districtName,
        Long departmentId,
        String departmentName,
        Long positionId,
        String positionName,
        boolean active,
        Instant createdAt
) {
}

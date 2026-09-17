package com.trackly.locationtracking.report.dto;

import java.time.LocalDate;

public record StopReportRow(
        String employeeFullName,
        String phoneNumber,
        String departmentName,
        String positionName,
        LocalDate date,
        String arrivalTime,
        String departureTime,
        int durationMinutes,
        String address,
        double latitude,
        double longitude
) {
}

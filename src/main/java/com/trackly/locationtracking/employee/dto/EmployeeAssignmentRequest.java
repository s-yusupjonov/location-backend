package com.trackly.locationtracking.employee.dto;

public record EmployeeAssignmentRequest(
        Long departmentId,
        Long positionId
) {
}

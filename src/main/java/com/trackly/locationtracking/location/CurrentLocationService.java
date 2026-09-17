package com.trackly.locationtracking.location;

import com.trackly.locationtracking.common.exception.ResourceNotFoundException;
import com.trackly.locationtracking.employee.EmployeeService;
import com.trackly.locationtracking.location.dto.LocationPingResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CurrentLocationService {

    private final EmployeeService employeeService;
    private final LocationPingRepository locationPingRepository;
    private final LocationPingMapper locationPingMapper;

    public CurrentLocationService(EmployeeService employeeService,
                                   LocationPingRepository locationPingRepository,
                                   LocationPingMapper locationPingMapper) {
        this.employeeService = employeeService;
        this.locationPingRepository = locationPingRepository;
        this.locationPingMapper = locationPingMapper;
    }

    public LocationPingResponse getCurrentLocation(Long employeeId) {
        employeeService.findById(employeeId);

        LocationPing latestPing = locationPingRepository.findFirstByEmployeeIdOrderByRecordedAtDesc(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("No location data found for employee id " + employeeId));

        return locationPingMapper.toResponse(latestPing);
    }
}

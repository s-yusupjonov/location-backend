package com.trackly.locationtracking.location;

import com.trackly.locationtracking.common.exception.ResourceNotFoundException;
import com.trackly.locationtracking.employee.Employee;
import com.trackly.locationtracking.employee.EmployeeRepository;
import com.trackly.locationtracking.location.dto.LocationPingRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class LocationIngestionService {

    private final EmployeeRepository employeeRepository;
    private final LocationPingRepository locationPingRepository;
    private final LocationPingMapper locationPingMapper;
    private final LocationBroadcastPublisher locationBroadcastPublisher;

    public LocationIngestionService(EmployeeRepository employeeRepository,
                                     LocationPingRepository locationPingRepository,
                                     LocationPingMapper locationPingMapper,
                                     LocationBroadcastPublisher locationBroadcastPublisher) {
        this.employeeRepository = employeeRepository;
        this.locationPingRepository = locationPingRepository;
        this.locationPingMapper = locationPingMapper;
        this.locationBroadcastPublisher = locationBroadcastPublisher;
    }

    @Transactional
    public LocationPing ingest(LocationPingRequest request) {
        Employee employee = employeeRepository.findByPhoneNumber(request.deviceId())
                .orElseThrow(() -> new ResourceNotFoundException("Unknown deviceId " + request.deviceId()));

        LocationPing locationPing = new LocationPing();
        locationPing.setEmployee(employee);
        locationPing.setLatitude(request.latitude());
        locationPing.setLongitude(request.longitude());
        locationPing.setAccuracy(request.accuracy());
        locationPing.setRecordedAt(Instant.ofEpochMilli(request.timestamp()));

        LocationPing savedLocationPing = locationPingRepository.save(locationPing);
        locationBroadcastPublisher.publish(employee.getId(), locationPingMapper.toResponse(savedLocationPing));

        return savedLocationPing;
    }
}

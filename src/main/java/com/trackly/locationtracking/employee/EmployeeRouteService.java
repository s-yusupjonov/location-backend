package com.trackly.locationtracking.employee;

import com.trackly.locationtracking.employee.dto.EmployeeRouteResponse;
import com.trackly.locationtracking.location.LocationPingMapper;
import com.trackly.locationtracking.location.LocationPingRepository;
import com.trackly.locationtracking.location.dto.LocationPingResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmployeeRouteService {

    private final EmployeeService employeeService;
    private final LocationPingRepository locationPingRepository;
    private final LocationPingMapper locationPingMapper;
    private final ZoneId serverZoneId;

    public EmployeeRouteService(EmployeeService employeeService,
                                 LocationPingRepository locationPingRepository,
                                 LocationPingMapper locationPingMapper,
                                 @Value("${server.time-zone}") String serverTimeZone) {
        this.employeeService = employeeService;
        this.locationPingRepository = locationPingRepository;
        this.locationPingMapper = locationPingMapper;
        this.serverZoneId = ZoneId.of(serverTimeZone);
    }

    public EmployeeRouteResponse getRoute(Long employeeId, LocalDate date) {
        employeeService.findById(employeeId);

        Instant startOfDay = date.atStartOfDay(serverZoneId).toInstant();
        Instant startOfNextDay = date.plusDays(1).atStartOfDay(serverZoneId).toInstant();

        List<LocationPingResponse> points = locationPingRepository
                .findByEmployeeIdAndRecordedAtGreaterThanEqualAndRecordedAtLessThanOrderByRecordedAtAsc(
                        employeeId, startOfDay, startOfNextDay)
                .stream()
                .map(locationPingMapper::toResponse)
                .toList();

        return new EmployeeRouteResponse(employeeId, date, points);
    }
}

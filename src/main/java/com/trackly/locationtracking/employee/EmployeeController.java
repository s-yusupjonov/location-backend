package com.trackly.locationtracking.employee;

import com.trackly.locationtracking.employee.dto.EmployeeAssignmentRequest;
import com.trackly.locationtracking.employee.dto.EmployeeRegistrationRequest;
import com.trackly.locationtracking.employee.dto.EmployeeResponse;
import com.trackly.locationtracking.employee.dto.EmployeeRouteResponse;
import com.trackly.locationtracking.location.CurrentLocationService;
import com.trackly.locationtracking.location.dto.LocationPingResponse;
import com.trackly.locationtracking.stop.StopDetectionService;
import com.trackly.locationtracking.stop.dto.StopResponse;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeRegistrationService employeeRegistrationService;
    private final EmployeeRouteService employeeRouteService;
    private final EmployeeMapper employeeMapper;
    private final StopDetectionService stopDetectionService;
    private final CurrentLocationService currentLocationService;

    public EmployeeController(EmployeeService employeeService,
                               EmployeeRegistrationService employeeRegistrationService,
                               EmployeeRouteService employeeRouteService,
                               EmployeeMapper employeeMapper,
                               StopDetectionService stopDetectionService,
                               CurrentLocationService currentLocationService) {
        this.employeeService = employeeService;
        this.employeeRegistrationService = employeeRegistrationService;
        this.employeeRouteService = employeeRouteService;
        this.employeeMapper = employeeMapper;
        this.stopDetectionService = stopDetectionService;
        this.currentLocationService = currentLocationService;
    }

    @PostMapping("/register")
    public ResponseEntity<EmployeeResponse> register(@Valid @RequestBody EmployeeRegistrationRequest request) {
        EmployeeResponse response = employeeMapper.toResponse(employeeRegistrationService.register(request));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping
    public List<EmployeeResponse> getAll(@RequestParam(required = false) Long regionId,
                                          @RequestParam(required = false) Long districtId,
                                          @RequestParam(required = false) Long departmentId) {
        return employeeService.findAll(regionId, districtId, departmentId).stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public EmployeeResponse getById(@PathVariable Long id) {
        return employeeMapper.toResponse(employeeService.findById(id));
    }

    @PatchMapping("/{id}/assignment")
    public EmployeeResponse updateAssignment(@PathVariable Long id,
                                              @RequestBody EmployeeAssignmentRequest request) {
        return employeeMapper.toResponse(employeeService.updateAssignment(id, request));
    }

    @GetMapping("/{id}/route")
    public EmployeeRouteResponse getRoute(@PathVariable Long id,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return employeeRouteService.getRoute(id, date);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        employeeService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/stops")
    public List<StopResponse> getStops(@PathVariable Long id,
                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return stopDetectionService.getStops(id, date);
    }

    @GetMapping("/{id}/current-location")
    public LocationPingResponse getCurrentLocation(@PathVariable Long id) {
        return currentLocationService.getCurrentLocation(id);
    }
}

package com.trackly.locationtracking.employee;

import com.trackly.locationtracking.common.exception.ResourceNotFoundException;
import com.trackly.locationtracking.district.District;
import com.trackly.locationtracking.district.DistrictRepository;
import com.trackly.locationtracking.employee.dto.EmployeeRegistrationRequest;
import com.trackly.locationtracking.region.Region;
import com.trackly.locationtracking.region.RegionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeRegistrationService {

    private final EmployeeRepository employeeRepository;
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;

    public EmployeeRegistrationService(EmployeeRepository employeeRepository,
                                        RegionRepository regionRepository,
                                        DistrictRepository districtRepository) {
        this.employeeRepository = employeeRepository;
        this.regionRepository = regionRepository;
        this.districtRepository = districtRepository;
    }

    @Transactional
    public Employee register(EmployeeRegistrationRequest request) {
        Region region = regionRepository.findById(request.regionId())
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id " + request.regionId()));
        District district = districtRepository.findById(request.districtId())
                .orElseThrow(() -> new ResourceNotFoundException("District not found with id " + request.districtId()));

        Employee employee = employeeRepository.findByPhoneNumber(request.phoneNumber())
                .orElseGet(Employee::new);

        employee.setFirstName(request.firstName());
        employee.setLastName(request.lastName());
        employee.setPhoneNumber(request.phoneNumber());
        employee.setRegion(region);
        employee.setDistrict(district);

        return employeeRepository.save(employee);
    }
}

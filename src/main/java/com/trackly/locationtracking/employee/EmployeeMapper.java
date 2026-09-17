package com.trackly.locationtracking.employee;

import com.trackly.locationtracking.department.Department;
import com.trackly.locationtracking.district.District;
import com.trackly.locationtracking.employee.dto.EmployeeResponse;
import com.trackly.locationtracking.position.Position;
import com.trackly.locationtracking.region.Region;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public EmployeeResponse toResponse(Employee employee) {
        Region region = employee.getRegion();
        District district = employee.getDistrict();
        Department department = employee.getDepartment();
        Position position = employee.getPosition();

        return new EmployeeResponse(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getPhoneNumber(),
                region != null ? region.getId() : null,
                region != null ? region.getName() : null,
                district != null ? district.getId() : null,
                district != null ? district.getName() : null,
                department != null ? department.getId() : null,
                department != null ? department.getName() : null,
                position != null ? position.getId() : null,
                position != null ? position.getName() : null,
                employee.isActive(),
                employee.getCreatedAt()
        );
    }
}

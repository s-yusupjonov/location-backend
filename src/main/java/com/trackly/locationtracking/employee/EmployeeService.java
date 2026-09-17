package com.trackly.locationtracking.employee;

import com.trackly.locationtracking.common.exception.ResourceNotFoundException;
import com.trackly.locationtracking.department.Department;
import com.trackly.locationtracking.department.DepartmentRepository;
import com.trackly.locationtracking.employee.dto.EmployeeAssignmentRequest;
import com.trackly.locationtracking.position.Position;
import com.trackly.locationtracking.position.PositionRepository;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;

    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository,
                           PositionRepository positionRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.positionRepository = positionRepository;
    }

    public List<Employee> findAll(Long regionId, Long districtId, Long departmentId) {
        Specification<Employee> specification =
                buildFilterSpecification(regionId, districtId, departmentId);

        return employeeRepository.findAll(specification);
    }

    public Employee findById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found with id " + id));
    }

    @Transactional
    public Employee updateAssignment(Long id, EmployeeAssignmentRequest request) {
        Employee employee = findById(id);

        Department department = null;

        if (request.departmentId() != null) {
            department = departmentRepository.findById(request.departmentId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Department not found with id " + request.departmentId()));
        }

        Position position = null;

        if (request.positionId() != null) {
            position = positionRepository.findById(request.positionId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Position not found with id " + request.positionId()));
        }

        employee.setDepartment(department);
        employee.setPosition(position);

        return employeeRepository.save(employee);
    }

    @Transactional
    public void deactivate(Long id) {
        Employee employee = findById(id);
        employee.setActive(false);
        employeeRepository.save(employee);
    }

    private Specification<Employee> buildFilterSpecification(
            Long regionId,
            Long districtId,
            Long departmentId) {

        return (root, query, criteriaBuilder) -> {

            /*
             * Fetch the relationships required by EmployeeMapper.
             *
             * This prevents LazyInitializationException when the
             * controller converts Employee entities to EmployeeResponse.
             */
            if (query != null && query.getResultType() != Long.class) {
                root.fetch("region", JoinType.LEFT);
                root.fetch("district", JoinType.LEFT);
                root.fetch("department", JoinType.LEFT);
                root.fetch("position", JoinType.LEFT);
                query.distinct(true);
            }

            List<Predicate> predicates = new ArrayList<>();

            if (regionId != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("region").get("id"),
                                regionId
                        )
                );
            }

            if (districtId != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("district").get("id"),
                                districtId
                        )
                );
            }

            if (departmentId != null) {
                predicates.add(
                        criteriaBuilder.equal(
                                root.get("department").get("id"),
                                departmentId
                        )
                );
            }

            return criteriaBuilder.and(
                    predicates.toArray(new Predicate[0])
            );
        };
    }
}
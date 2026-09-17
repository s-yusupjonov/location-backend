package com.trackly.locationtracking.department;

import com.trackly.locationtracking.common.exception.DuplicateResourceException;
import com.trackly.locationtracking.common.exception.ResourceNotFoundException;
import com.trackly.locationtracking.department.dto.DepartmentRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> findAll() {
        return departmentRepository.findAll();
    }

    public Department findById(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
    }

    @Transactional
    public Department create(DepartmentRequest request) {
        if (departmentRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Department already exists with name " + request.name());
        }
        Department department = new Department();
        department.setName(request.name());
        return departmentRepository.save(department);
    }

    @Transactional
    public Department update(Long id, DepartmentRequest request) {
        Department department = findById(id);
        if (!department.getName().equalsIgnoreCase(request.name())
                && departmentRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Department already exists with name " + request.name());
        }
        department.setName(request.name());
        return departmentRepository.save(department);
    }

    @Transactional
    public void delete(Long id) {
        Department department = findById(id);
        departmentRepository.delete(department);
    }
}

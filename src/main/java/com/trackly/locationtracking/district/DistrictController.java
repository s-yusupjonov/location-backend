package com.trackly.locationtracking.district;

import com.trackly.locationtracking.district.dto.DistrictRequest;
import com.trackly.locationtracking.district.dto.DistrictResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/districts")
public class DistrictController {

    private final DistrictService districtService;
    private final DistrictMapper districtMapper;

    public DistrictController(DistrictService districtService, DistrictMapper districtMapper) {
        this.districtService = districtService;
        this.districtMapper = districtMapper;
    }

    @GetMapping
    public List<DistrictResponse> getAll(@RequestParam(required = false) Long regionId) {
        return districtService.findAll(regionId).stream().map(districtMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public DistrictResponse getById(@PathVariable Long id) {
        return districtMapper.toResponse(districtService.findById(id));
    }

    @PostMapping
    public ResponseEntity<DistrictResponse> create(@Valid @RequestBody DistrictRequest request) {
        DistrictResponse response = districtMapper.toResponse(districtService.create(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public DistrictResponse update(@PathVariable Long id, @Valid @RequestBody DistrictRequest request) {
        return districtMapper.toResponse(districtService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        districtService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

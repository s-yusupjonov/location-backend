package com.trackly.locationtracking.region;

import com.trackly.locationtracking.region.dto.RegionRequest;
import com.trackly.locationtracking.region.dto.RegionResponse;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/regions")
public class RegionController {

    private final RegionService regionService;
    private final RegionMapper regionMapper;

    public RegionController(RegionService regionService, RegionMapper regionMapper) {
        this.regionService = regionService;
        this.regionMapper = regionMapper;
    }

    @GetMapping
    public List<RegionResponse> getAll() {
        return regionService.findAll().stream().map(regionMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public RegionResponse getById(@PathVariable Long id) {
        return regionMapper.toResponse(regionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<RegionResponse> create(@Valid @RequestBody RegionRequest request) {
        RegionResponse response = regionMapper.toResponse(regionService.create(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public RegionResponse update(@PathVariable Long id, @Valid @RequestBody RegionRequest request) {
        return regionMapper.toResponse(regionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        regionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

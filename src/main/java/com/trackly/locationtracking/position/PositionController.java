package com.trackly.locationtracking.position;

import com.trackly.locationtracking.position.dto.PositionRequest;
import com.trackly.locationtracking.position.dto.PositionResponse;
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
@RequestMapping("/api/positions")
public class PositionController {

    private final PositionService positionService;
    private final PositionMapper positionMapper;

    public PositionController(PositionService positionService, PositionMapper positionMapper) {
        this.positionService = positionService;
        this.positionMapper = positionMapper;
    }

    @GetMapping
    public List<PositionResponse> getAll() {
        return positionService.findAll().stream().map(positionMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public PositionResponse getById(@PathVariable Long id) {
        return positionMapper.toResponse(positionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PositionResponse> create(@Valid @RequestBody PositionRequest request) {
        PositionResponse response = positionMapper.toResponse(positionService.create(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public PositionResponse update(@PathVariable Long id, @Valid @RequestBody PositionRequest request) {
        return positionMapper.toResponse(positionService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        positionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

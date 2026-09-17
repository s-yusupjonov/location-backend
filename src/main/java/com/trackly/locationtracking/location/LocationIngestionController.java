package com.trackly.locationtracking.location;

import com.trackly.locationtracking.location.dto.LocationPingRequest;
import com.trackly.locationtracking.location.dto.LocationPingResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/locations")
public class LocationIngestionController {

    private final LocationIngestionService locationIngestionService;
    private final LocationPingMapper locationPingMapper;

    public LocationIngestionController(LocationIngestionService locationIngestionService,
                                        LocationPingMapper locationPingMapper) {
        this.locationIngestionService = locationIngestionService;
        this.locationPingMapper = locationPingMapper;
    }

    @PostMapping
    public ResponseEntity<LocationPingResponse> ingest(@Valid @RequestBody LocationPingRequest request) {
        LocationPingResponse response = locationPingMapper.toResponse(locationIngestionService.ingest(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

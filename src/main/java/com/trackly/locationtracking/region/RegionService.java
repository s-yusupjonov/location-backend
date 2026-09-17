package com.trackly.locationtracking.region;

import com.trackly.locationtracking.common.exception.DuplicateResourceException;
import com.trackly.locationtracking.common.exception.ResourceNotFoundException;
import com.trackly.locationtracking.region.dto.RegionRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RegionService {

    private final RegionRepository regionRepository;

    public RegionService(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    public List<Region> findAll() {
        return regionRepository.findAll();
    }

    public Region findById(Long id) {
        return regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id " + id));
    }

    @Transactional
    public Region create(RegionRequest request) {
        if (regionRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Region already exists with name " + request.name());
        }
        Region region = new Region();
        region.setName(request.name());
        return regionRepository.save(region);
    }

    @Transactional
    public Region update(Long id, RegionRequest request) {
        Region region = findById(id);
        if (!region.getName().equalsIgnoreCase(request.name())
                && regionRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Region already exists with name " + request.name());
        }
        region.setName(request.name());
        return regionRepository.save(region);
    }

    @Transactional
    public void delete(Long id) {
        Region region = findById(id);
        regionRepository.delete(region);
    }
}

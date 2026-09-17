package com.trackly.locationtracking.district;

import com.trackly.locationtracking.common.exception.DuplicateResourceException;
import com.trackly.locationtracking.common.exception.ResourceNotFoundException;
import com.trackly.locationtracking.district.dto.DistrictRequest;
import com.trackly.locationtracking.region.Region;
import com.trackly.locationtracking.region.RegionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final RegionRepository regionRepository;

    public DistrictService(DistrictRepository districtRepository, RegionRepository regionRepository) {
        this.districtRepository = districtRepository;
        this.regionRepository = regionRepository;
    }

    public List<District> findAll(Long regionId) {
        if (regionId != null) {
            return districtRepository.findByRegionId(regionId);
        }

        return districtRepository.findAllWithRegion();
    }

    public District findById(Long id) {
        return districtRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("District not found with id " + id));
    }

    @Transactional
    public District create(DistrictRequest request) {
        Region region = findRegion(request.regionId());
        if (districtRepository.existsByNameIgnoreCaseAndRegionId(request.name(), request.regionId())) {
            throw new DuplicateResourceException(
                    "District already exists with name " + request.name() + " in the given region");
        }
        District district = new District();
        district.setName(request.name());
        district.setRegion(region);
        return districtRepository.save(district);
    }

    @Transactional
    public District update(Long id, DistrictRequest request) {
        District district = findById(id);
        Region region = findRegion(request.regionId());

        boolean nameChanged = !district.getName().equalsIgnoreCase(request.name())
                || !district.getRegion().getId().equals(request.regionId());
        if (nameChanged && districtRepository.existsByNameIgnoreCaseAndRegionId(request.name(), request.regionId())) {
            throw new DuplicateResourceException(
                    "District already exists with name " + request.name() + " in the given region");
        }

        district.setName(request.name());
        district.setRegion(region);
        return districtRepository.save(district);
    }

    @Transactional
    public void delete(Long id) {
        District district = findById(id);
        districtRepository.delete(district);
    }

    private Region findRegion(Long regionId) {
        return regionRepository.findById(regionId)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id " + regionId));
    }
}

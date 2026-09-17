package com.trackly.locationtracking.position;

import com.trackly.locationtracking.common.exception.DuplicateResourceException;
import com.trackly.locationtracking.common.exception.ResourceNotFoundException;
import com.trackly.locationtracking.position.dto.PositionRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    public List<Position> findAll() {
        return positionRepository.findAll();
    }

    public Position findById(Long id) {
        return positionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Position not found with id " + id));
    }

    @Transactional
    public Position create(PositionRequest request) {
        if (positionRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Position already exists with name " + request.name());
        }
        Position position = new Position();
        position.setName(request.name());
        return positionRepository.save(position);
    }

    @Transactional
    public Position update(Long id, PositionRequest request) {
        Position position = findById(id);
        if (!position.getName().equalsIgnoreCase(request.name())
                && positionRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Position already exists with name " + request.name());
        }
        position.setName(request.name());
        return positionRepository.save(position);
    }

    @Transactional
    public void delete(Long id) {
        Position position = findById(id);
        positionRepository.delete(position);
    }
}

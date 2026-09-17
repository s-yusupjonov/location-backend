package com.trackly.locationtracking.location;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface LocationPingRepository extends JpaRepository<LocationPing, Long> {

    List<LocationPing> findByEmployeeIdAndRecordedAtGreaterThanEqualAndRecordedAtLessThanOrderByRecordedAtAsc(
            Long employeeId, Instant startInclusive, Instant endExclusive);

    Optional<LocationPing> findFirstByEmployeeIdOrderByRecordedAtDesc(Long employeeId);
}

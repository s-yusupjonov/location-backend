package com.trackly.locationtracking.stop;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface StopRepository extends JpaRepository<Stop, Long> {

    Optional<Stop> findByEmployeeIdAndArrivalTimeAndDepartureTime(
            Long employeeId, Instant arrivalTime, Instant departureTime);
}

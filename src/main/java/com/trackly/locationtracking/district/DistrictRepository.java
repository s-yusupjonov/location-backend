package com.trackly.locationtracking.district;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Long> {

    @Query("""
            SELECT d
            FROM District d
            JOIN FETCH d.region
            WHERE d.region.id = :regionId
            """)
    List<District> findByRegionId(@Param("regionId") Long regionId);

    @Query("""
            SELECT d
            FROM District d
            JOIN FETCH d.region
            """)
    List<District> findAllWithRegion();

    Optional<District> findByNameIgnoreCaseAndRegionId(String name, Long regionId);

    boolean existsByNameIgnoreCaseAndRegionId(String name, Long regionId);
}
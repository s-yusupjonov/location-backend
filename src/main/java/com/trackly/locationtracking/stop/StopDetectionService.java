package com.trackly.locationtracking.stop;

import com.trackly.locationtracking.employee.Employee;
import com.trackly.locationtracking.employee.EmployeeService;
import com.trackly.locationtracking.geocoding.YandexGeocodingClient;
import com.trackly.locationtracking.location.LocationPing;
import com.trackly.locationtracking.location.LocationPingRepository;
import com.trackly.locationtracking.stop.dto.StopResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.ToDoubleFunction;

@Service
@Transactional(readOnly = true)
public class StopDetectionService {

    private final EmployeeService employeeService;
    private final LocationPingRepository locationPingRepository;
    private final StopRepository stopRepository;
    private final YandexGeocodingClient yandexGeocodingClient;
    private final StopMapper stopMapper;
    private final StopDetectionProperties properties;
    private final ZoneId serverZoneId;

    public StopDetectionService(EmployeeService employeeService,
                                 LocationPingRepository locationPingRepository,
                                 StopRepository stopRepository,
                                 YandexGeocodingClient yandexGeocodingClient,
                                 StopMapper stopMapper,
                                 StopDetectionProperties properties,
                                 @Value("${server.time-zone}") String serverTimeZone) {
        this.employeeService = employeeService;
        this.locationPingRepository = locationPingRepository;
        this.stopRepository = stopRepository;
        this.yandexGeocodingClient = yandexGeocodingClient;
        this.stopMapper = stopMapper;
        this.properties = properties;
        this.serverZoneId = ZoneId.of(serverTimeZone);
    }

    @Transactional
    public List<StopResponse> getStops(Long employeeId, LocalDate date) {
        Employee employee = employeeService.findById(employeeId);

        Instant startOfDay = date.atStartOfDay(serverZoneId).toInstant();
        Instant startOfNextDay = date.plusDays(1).atStartOfDay(serverZoneId).toInstant();

        List<LocationPing> pings = locationPingRepository
                .findByEmployeeIdAndRecordedAtGreaterThanEqualAndRecordedAtLessThanOrderByRecordedAtAsc(
                        employeeId, startOfDay, startOfNextDay);

        return detectCandidates(pings).stream()
                .map(candidate -> resolveStop(employee, candidate))
                .sorted(Comparator.comparing(Stop::getArrivalTime))
                .map(stopMapper::toResponse)
                .toList();
    }

    private List<StopCandidate> detectCandidates(List<LocationPing> pings) {
        List<StopCandidate> candidates = new ArrayList<>();
        List<LocationPing> currentGroup = new ArrayList<>();

        for (LocationPing ping : pings) {
            if (currentGroup.isEmpty() || isWithinRadius(currentGroup, ping)) {
                currentGroup.add(ping);
                continue;
            }
            addCandidateIfStop(candidates, currentGroup);
            currentGroup = new ArrayList<>();
            currentGroup.add(ping);
        }
        addCandidateIfStop(candidates, currentGroup);

        return candidates;
    }

    private boolean isWithinRadius(List<LocationPing> group, LocationPing ping) {
        double centroidLatitude = average(group, LocationPing::getLatitude);
        double centroidLongitude = average(group, LocationPing::getLongitude);
        double distance = HaversineDistanceCalculator.distanceMeters(
                centroidLatitude, centroidLongitude, ping.getLatitude(), ping.getLongitude());
        return distance <= properties.radiusMeters();
    }

    private void addCandidateIfStop(List<StopCandidate> candidates, List<LocationPing> group) {
        if (group.size() < 2) {
            return;
        }

        Instant arrivalTime = group.get(0).getRecordedAt();
        Instant departureTime = group.get(group.size() - 1).getRecordedAt();
        long durationMinutes = Duration.between(arrivalTime, departureTime).toMinutes();

        if (durationMinutes < properties.minDurationMinutes()) {
            return;
        }

        double centroidLatitude = average(group, LocationPing::getLatitude);
        double centroidLongitude = average(group, LocationPing::getLongitude);
        candidates.add(new StopCandidate(centroidLatitude, centroidLongitude, arrivalTime, departureTime,
                (int) durationMinutes));
    }

    private Stop resolveStop(Employee employee, StopCandidate candidate) {
        return stopRepository.findByEmployeeIdAndArrivalTimeAndDepartureTime(
                        employee.getId(), candidate.arrivalTime(), candidate.departureTime())
                .orElseGet(() -> createStop(employee, candidate));
    }

    private Stop createStop(Employee employee, StopCandidate candidate) {
        Stop stop = new Stop();
        stop.setEmployee(employee);
        stop.setLatitude(candidate.latitude());
        stop.setLongitude(candidate.longitude());
        stop.setArrivalTime(candidate.arrivalTime());
        stop.setDepartureTime(candidate.departureTime());
        stop.setDurationMinutes(candidate.durationMinutes());
        stop.setAddress(yandexGeocodingClient.reverseGeocode(candidate.latitude(), candidate.longitude()));
        return stopRepository.save(stop);
    }

    private double average(List<LocationPing> pings, ToDoubleFunction<LocationPing> extractor) {
        return pings.stream().mapToDouble(extractor).average().orElseThrow();
    }

    private record StopCandidate(
            double latitude,
            double longitude,
            Instant arrivalTime,
            Instant departureTime,
            int durationMinutes
    ) {
    }
}

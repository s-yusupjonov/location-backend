package com.trackly.locationtracking.report;

import com.trackly.locationtracking.department.DepartmentService;
import com.trackly.locationtracking.employee.Employee;
import com.trackly.locationtracking.employee.EmployeeService;
import com.trackly.locationtracking.report.dto.StopReportRow;
import com.trackly.locationtracking.stop.StopDetectionService;
import com.trackly.locationtracking.stop.dto.StopResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class StopReportService {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final StopDetectionService stopDetectionService;
    private final StopReportWorkbookBuilder stopReportWorkbookBuilder;
    private final DateTimeFormatter timeFormatter;

    public StopReportService(EmployeeService employeeService,
                              DepartmentService departmentService,
                              StopDetectionService stopDetectionService,
                              StopReportWorkbookBuilder stopReportWorkbookBuilder,
                              @Value("${server.time-zone}") String serverTimeZone) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
        this.stopDetectionService = stopDetectionService;
        this.stopReportWorkbookBuilder = stopReportWorkbookBuilder;
        this.timeFormatter = DateTimeFormatter.ofPattern("HH:mm").withZone(ZoneId.of(serverTimeZone));
    }

    public byte[] generateEmployeeReport(Long employeeId, LocalDate from, LocalDate to) {
        validateRange(from, to);
        Employee employee = employeeService.findById(employeeId);
        List<StopReportRow> rows = buildRows(employee, from, to);
        return stopReportWorkbookBuilder.build("Stops", rows);
    }

    public byte[] generateDepartmentReport(Long departmentId, LocalDate from, LocalDate to) {
        validateRange(from, to);
        departmentService.findById(departmentId);

        List<StopReportRow> rows = new ArrayList<>();
        for (Employee employee : employeeService.findAll(null, null, departmentId)) {
            rows.addAll(buildRows(employee, from, to));
        }
        return stopReportWorkbookBuilder.build("Department Stops", rows);
    }

    private List<StopReportRow> buildRows(Employee employee, LocalDate from, LocalDate to) {
        List<StopReportRow> rows = new ArrayList<>();
        for (LocalDate date = from; !date.isAfter(to); date = date.plusDays(1)) {
            for (StopResponse stop : stopDetectionService.getStops(employee.getId(), date)) {
                rows.add(toRow(employee, date, stop));
            }
        }
        return rows;
    }

    private StopReportRow toRow(Employee employee, LocalDate date, StopResponse stop) {
        return new StopReportRow(
                employee.getFirstName() + " " + employee.getLastName(),
                employee.getPhoneNumber(),
                employee.getDepartment() != null ? employee.getDepartment().getName() : "",
                employee.getPosition() != null ? employee.getPosition().getName() : "",
                date,
                timeFormatter.format(stop.arrivalTime()),
                timeFormatter.format(stop.departureTime()),
                stop.durationMinutes(),
                stop.address(),
                stop.latitude(),
                stop.longitude()
        );
    }

    private void validateRange(LocalDate from, LocalDate to) {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("from must not be after to");
        }
    }
}

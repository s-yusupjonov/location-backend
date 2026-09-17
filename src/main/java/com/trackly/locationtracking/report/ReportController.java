package com.trackly.locationtracking.report;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private static final MediaType XLSX_MEDIA_TYPE =
            MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

    private final StopReportService stopReportService;

    public ReportController(StopReportService stopReportService) {
        this.stopReportService = stopReportService;
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<byte[]> getEmployeeReport(@PathVariable Long id,
                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        byte[] workbook = stopReportService.generateEmployeeReport(id, from, to);
        return buildDownloadResponse(workbook, "employee-" + id + "-stops.xlsx");
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<byte[]> getDepartmentReport(@PathVariable Long id,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
                                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {
        byte[] workbook = stopReportService.generateDepartmentReport(id, from, to);
        return buildDownloadResponse(workbook, "department-" + id + "-stops.xlsx");
    }

    private ResponseEntity<byte[]> buildDownloadResponse(byte[] workbook, String filename) {
        return ResponseEntity.ok()
                .contentType(XLSX_MEDIA_TYPE)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .body(workbook);
    }
}

package com.trackly.locationtracking.report;

import com.trackly.locationtracking.report.dto.StopReportRow;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import java.util.Locale;

@Component
public class StopReportWorkbookBuilder {

    private static final List<String> HEADERS = List.of(
            "Employee", "Phone Number", "Department", "Position",
            "Date", "Arrival Time", "Departure Time", "Duration (minutes)", "Address"
    );
    private static final String MAP_LINK_TEMPLATE = "https://yandex.com/maps/?pt=%s,%s&z=16&l=map";

    public byte[] build(String sheetName, List<StopReportRow> rows) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet(sheetName);
            CreationHelper creationHelper = workbook.getCreationHelper();
            CellStyle headerStyle = createHeaderStyle(workbook);
            CellStyle hyperlinkStyle = createHyperlinkStyle(workbook);

            writeHeaderRow(sheet, headerStyle);

            int rowIndex = 1;
            for (StopReportRow row : rows) {
                writeDataRow(sheet, rowIndex++, row, creationHelper, hyperlinkStyle);
            }

            for (int column = 0; column < HEADERS.size(); column++) {
                sheet.autoSizeColumn(column);
            }

            return toByteArray(workbook);
        } catch (IOException exception) {
            throw new UncheckedIOException("Failed to generate stop report workbook", exception);
        }
    }

    private void writeHeaderRow(Sheet sheet, CellStyle headerStyle) {
        Row headerRow = sheet.createRow(0);
        for (int column = 0; column < HEADERS.size(); column++) {
            Cell cell = headerRow.createCell(column);
            cell.setCellValue(HEADERS.get(column));
            cell.setCellStyle(headerStyle);
        }
    }

    private void writeDataRow(Sheet sheet, int rowIndex, StopReportRow row,
                               CreationHelper creationHelper, CellStyle hyperlinkStyle) {
        Row excelRow = sheet.createRow(rowIndex);
        excelRow.createCell(0).setCellValue(row.employeeFullName());
        excelRow.createCell(1).setCellValue(row.phoneNumber());
        excelRow.createCell(2).setCellValue(row.departmentName());
        excelRow.createCell(3).setCellValue(row.positionName());
        excelRow.createCell(4).setCellValue(row.date().toString());
        excelRow.createCell(5).setCellValue(row.arrivalTime());
        excelRow.createCell(6).setCellValue(row.departureTime());
        excelRow.createCell(7).setCellValue(row.durationMinutes());

        Cell addressCell = excelRow.createCell(8);
        addressCell.setCellValue(row.address());
        addressCell.setHyperlink(createMapHyperlink(creationHelper, row));
        addressCell.setCellStyle(hyperlinkStyle);
    }

    private Hyperlink createMapHyperlink(CreationHelper creationHelper, StopReportRow row) {
        Hyperlink hyperlink = creationHelper.createHyperlink(HyperlinkType.URL);
        hyperlink.setAddress(String.format(Locale.ROOT, MAP_LINK_TEMPLATE, row.longitude(), row.latitude()));
        return hyperlink;
    }

    private CellStyle createHeaderStyle(Workbook workbook) {
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        CellStyle style = workbook.createCellStyle();
        style.setFont(headerFont);
        return style;
    }

    private CellStyle createHyperlinkStyle(Workbook workbook) {
        Font hyperlinkFont = workbook.createFont();
        hyperlinkFont.setUnderline(Font.U_SINGLE);
        hyperlinkFont.setColor(IndexedColors.BLUE.getIndex());
        CellStyle style = workbook.createCellStyle();
        style.setFont(hyperlinkFont);
        return style;
    }

    private byte[] toByteArray(Workbook workbook) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        return outputStream.toByteArray();
    }
}

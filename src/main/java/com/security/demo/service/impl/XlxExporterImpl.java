package com.security.demo.service.impl;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.security.demo.dto.ExportDTO;
import com.security.demo.dto.MessageResponseDTO;
import com.security.demo.service.Expoter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class XlxExporterImpl<T> implements Expoter<ExportDTO<T>, MessageResponseDTO> {

    public XSSFWorkbook workbook;

    public XSSFSheet sheet;

    public  XlxExporterImpl() {
        this.workbook = new XSSFWorkbook();
    }


    private HttpServletResponse initResponseForExportExcel(HttpServletResponse response, String fileName) {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName + "_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);
        return response;
    }

    private void writeTableHeaderExcel(String sheetName, String titleName, List<String> headers) {

        // sheet
        sheet = workbook.createSheet(sheetName);
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(20);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);

        // title
        createCell(row, 0, titleName, style);
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, headers.size() - 1));
        font.setFontHeightInPoints((short) 10);

        // header
        row = sheet.createRow(1);
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        for (int i = 0; i < headers.size(); i++) {
            createCell(row, i, headers.get(i), style);
        }
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        cell.setCellValue(Objects.isNull(value)?"":value.toString());
        cell.setCellStyle(style);
    }

    private CellStyle getFontContentExcel() {
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
        return style;
    }

    @Override
    public MessageResponseDTO exportAs(ExportDTO<T> data) throws IllegalAccessException, IOException {
        HttpServletResponse response=data.getHttpServletResponse();
        response = initResponseForExportExcel(response, data.getFileName());
        ServletOutputStream outputStream = response.getOutputStream();

        CellStyle style = getFontContentExcel();
        List<T> responseData=data.getData();
        Class<T> responseClass=data.getClassObj();


        List<String> headers= Arrays.stream(responseClass.getFields()).map(Field::getName).collect(Collectors.toList());


        //headers
        writeTableHeaderExcel(data.getSheetName(), data.getTitle(), headers);

        int startRow = 2;
        for (T tempData : responseData) {
            Row row = sheet.createRow(startRow++);
            int columnCount = 0;
            for (Field field :responseClass.getFields())
            {
                Object value=field.get(tempData);
                createCell(row, columnCount++, value, style);
            }

        }

        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

        return MessageResponseDTO.builder().message("Exported Successfully").build();
    }
}

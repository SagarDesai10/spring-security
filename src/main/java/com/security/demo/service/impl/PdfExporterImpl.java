package com.security.demo.service.impl;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.security.demo.dto.ExportDTO;
import com.security.demo.dto.MessageResponseDTO;
import com.security.demo.service.Expoter;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.awt.*;
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
public class PdfExporterImpl<T> implements Expoter<ExportDTO<T>, MessageResponseDTO> {
    @Override
    public MessageResponseDTO exportAs(ExportDTO<T> data) throws
        IllegalAccessException, IOException {

        HttpServletResponse response=data.getHttpServletResponse();
        response=initResponseForExportExcel(response, data.getFileName());
        Document document=new Document();
        PdfWriter.getInstance(document,response.getOutputStream());

        document.open();

        setTitle(document,data.getTitle());

        List<String> headers= Arrays.stream(data.getClassObj().getFields()).map(Field::getName).collect(Collectors.toList());

        PdfPTable table=new PdfPTable(headers.size());
        table.setWidthPercentage(100f);
        table.setSpacingBefore(20);
        table.setHeaderRows(1);

        float[] columnWidths = new float[headers.size()];
        for (int i = 0; i < headers.size(); i++) {
//            if (headers.get(i).toLowerCase().equals("id")) {
//                columnWidths[i] = 2f; // Narrow for IDs
//            } else if (headers.get(i).toLowerCase().contains("email")) {
//                columnWidths[i] = 9f; // Wider for emails
//            } else if (headers.get(i).toLowerCase().contains("company") || headers.get(i).toLowerCase().contains("name")) {
//                columnWidths[i] = 5f; // Wider for names
//            }  else if (headers.get(i).toLowerCase().equals("profiletype")) {
//                columnWidths[i] = 6f;
//            } else {
//                columnWidths[i] = 5f; // Default
//            }
        	
        	columnWidths[i]=5f;
        }
        table.setWidths(columnWidths);

        writeTableHeader(table,headers);
        Font dataFont = FontFactory.getFont(FontFactory.HELVETICA, 6);
        for (T tempData : data.getData()) {
            for (Field field :data.getClassObj().getFields())
            {
                Object value=field.get(tempData);
//                table.addCell(Objects.isNull(value)?"":value.toString());
                PdfPCell cell = new PdfPCell(new Phrase(Objects.toString(value, ""),dataFont));
                cell.setPadding(4);
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(cell);
            }

        }

        document.add(table);
        document.close();

        return MessageResponseDTO.builder().message("Exported Successfully").build();
    }

    private void setTitle(Document document,String title)
    {
        Font font= FontFactory.getFont(FontFactory.TIMES_ROMAN);
        font.setSize(18);
        font.setColor(Color.BLUE);

        Paragraph p=new Paragraph(title,font);
        p.setAlignment(Element.ALIGN_CENTER);
        document.add(p);
    }

    private HttpServletResponse initResponseForExportExcel(HttpServletResponse response, String fileName) {
        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName + "_" + currentDateTime + ".pdf";
        response.setHeader(headerKey, headerValue);
        return response;
    }

    private void writeTableHeader(PdfPTable pdfTable,List<String> headers)
    {
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD,6);
        headerFont.setColor(Color.WHITE);

        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, headerFont));
            cell.setBackgroundColor(Color.DARK_GRAY);
            cell.setPadding(4);
            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
            pdfTable.addCell(cell);
        }

    }
}

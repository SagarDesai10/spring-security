package com.security.demo.service.impl;

import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.MappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import com.security.demo.dto.ExportDTO;
import com.security.demo.service.Expoter;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class CsvExporterImpl<T> implements Expoter<ExportDTO<T>, StringWriter> {

    @Override
    public StringWriter exportAs(ExportDTO<T> data) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException {

        HttpServletResponse response= data.getHttpServletResponse();
        response=initResponseForExportExcel(response, data.getFileName());

        StringWriter stringWriter=new StringWriter();

        MappingStrategy<T> columnStrategy = new HeaderColumnNameMappingStrategy<>();
        columnStrategy.setType(data.getClassObj());
        
        StatefulBeanToCsv<T> csvBuilder =
        	    new StatefulBeanToCsvBuilder<T>(stringWriter) 
        	        .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
        	        .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
        	        .withMappingStrategy(columnStrategy)
        	        .build();

        csvBuilder.write(data.getData());
        return stringWriter;

    }

    private HttpServletResponse initResponseForExportExcel(HttpServletResponse response, String fileName) {
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd:hh:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName + "_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);
        return response;
    }
}

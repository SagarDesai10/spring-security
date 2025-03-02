package com.security.demo.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.security.demo.service.Expoter;
import com.security.demo.service.ExpoterService;

@Service
public class ExporterServiceImpl implements ExpoterService {
    @Override
    public Expoter getExpoter(String type) {

       return switch (type)
        {
            case "CSV" -> new CsvExporterImpl();
            case "EXCEL" -> new XlxExporterImpl<>();
            case "PDF" -> new PdfExporterImpl<>();
            default -> throw new RuntimeException("Inavalid Request");
        };

    }
}

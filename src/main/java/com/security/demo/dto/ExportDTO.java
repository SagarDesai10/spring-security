package com.security.demo.dto;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExportDTO<T> {

    private List<T> data;
    private Class<T> classObj;
    private HttpServletResponse httpServletResponse;
    private String fileName;
    private String title;
    private String sheetName;

}

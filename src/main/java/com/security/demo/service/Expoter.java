package com.security.demo.service;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import java.io.IOException;


public interface Expoter<T,R> {
    R exportAs(T data) throws CsvRequiredFieldEmptyException, CsvDataTypeMismatchException, IllegalAccessException,
        IOException;
}

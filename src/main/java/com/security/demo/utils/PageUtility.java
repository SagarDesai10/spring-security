package com.security.demo.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PageUtility {

    public static Pageable getPageable(Integer page, Integer size) {
        return PageRequest.of(getPage(page), getSize(size));
    }


    public static Integer getPage(Integer page) {

        if (page >= 0)
            return page;

        return StringLiterals.PAGE;

    }

    public static Integer getSize(Integer size) {

        if (size > 0)
            return size;

        return StringLiterals.SIZE;
    }

}

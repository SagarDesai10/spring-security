package com.security.demo.enums;


import com.security.demo.dto.SortRequestDTO;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;

public enum SortDirectionEnum {

    ASC {
        public <T> Order build(Root<T> root, CriteriaBuilder cb, SortRequestDTO request) {
            return cb.asc(root.get(request.getKey()));
        }
    },

    DESC {
        public <T> Order build(Root<T> root, CriteriaBuilder cb, SortRequestDTO request) {
            return cb.desc(root.get(request.getKey()));
        }
    };

    public abstract <T> Order build(Root<T> root, CriteriaBuilder cb, SortRequestDTO request);
}

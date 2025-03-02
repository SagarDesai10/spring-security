package com.security.demo.enums;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.security.demo.dto.FilterRequestDTO;
import com.security.demo.utils.StringLiterals;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public enum SearchOperatorEnum {

    EQUAL {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, Predicate predicate, FilterRequestDTO request) {

            Object value = request.getValues().get(0);
            Expression<?> key = this.getPath(root, request);
            if (StringLiterals.OR.equals(request.getFilter())) return cb.or(cb.equal(key, value), predicate);

            return cb.and(cb.equal(key, value), predicate);

        }

    },

    NOT_EQUAL {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, Predicate predicate, FilterRequestDTO request) {

            Object value = request.getValues().get(0);
            Expression<?> key = this.getPath(root, request);
            if (StringLiterals.OR.equals(request.getFilter())) return cb.or(cb.notEqual(key, value), predicate);

            return cb.and(cb.notEqual(key, value), predicate);

        }
    },

    LIKE {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, Predicate predicate, FilterRequestDTO request) {

            Expression<String> key = this.getPath(root, request);
            if (StringLiterals.OR.equals(request.getFilter()))
                return cb.or(cb.like(cb.upper(key), "%" + request.getValues().get(0).toString().toUpperCase() + "%"),
                    predicate);

            return cb.and(cb.like(cb.upper(key), "%" + request.getValues().get(0).toString().toUpperCase() + "%"),
                predicate);

        }

    },

    NOT_LIKE {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, Predicate predicate, FilterRequestDTO request) {

            Expression<String> key = this.getPath(root, request);
            if (StringLiterals.OR.equals(request.getFilter()))
                return cb.or(cb.notLike(cb.upper(key), "%" + request.getValues().get(0).toString().toUpperCase() +
                    "%"), predicate);

            return cb.and(cb.notLike(cb.upper(key), "%" + request.getValues().get(0).toString().toUpperCase() + "%"),
                predicate);

        }

    },

    IN {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, Predicate predicate, FilterRequestDTO request) {

            List<Object> values = request.getValues();
            Expression<?> key = this.getPath(root, request);
            CriteriaBuilder.In<Object> inClause = cb.in(key);
            for (Object value : values) {
                inClause.value(value);
            }

            if (StringLiterals.OR.equals(request.getFilter())) return cb.or(inClause, predicate);

            return cb.and(inClause, predicate);
        }
    },

    NOT_IN {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, Predicate predicate, FilterRequestDTO request) {
            List<Object> values = request.getValues();
            Expression<?> key = this.getPath(root, request);
            CriteriaBuilder.In<Object> inClause = cb.in(key);
            for (Object value : values) {
                inClause.value(value);
            }

            if (StringLiterals.OR.equals(request.getFilter())) return cb.or(cb.not(inClause), predicate);

            return cb.and(cb.not(inClause), predicate);
        }

    },

    BETWEEN {
        public <T> Predicate build(Root<T> root, CriteriaBuilder cb, Predicate predicate, FilterRequestDTO request) {

            Object value = request.getValues().get(0);
            Object valueTo = Objects.isNull(request.getValues().get(1)) ? value : request.getValues().get(1);
            String type = root.get(request.getKey()).getJavaType().getSimpleName();

            if (FIELD_TYPE.contains(type)) {
                Expression<Number> key = this.getPath(root, request);
                if (StringLiterals.OR.equals(request.getFilter()))
                    return cb.or(cb.and(cb.ge(key, (Number) value), cb.le(key, (Number) valueTo)), predicate);

                return cb.and(cb.and(cb.ge(key, (Number) value), cb.le(key, (Number) valueTo)), predicate);

            }

            if (StringLiterals.Timestamp.equals(type)) {
                Expression<ZonedDateTime> key = this.getPath(root, request);
                if (StringLiterals.OR.equals(request.getFilter()))
                    return cb.or(cb.and(cb.greaterThanOrEqualTo(key, ZonedDateTime.parse(value.toString())),
                        cb.lessThanOrEqualTo(key, ZonedDateTime.parse(valueTo.toString()))), predicate);

                return cb.and(cb.and(cb.greaterThanOrEqualTo(key, ZonedDateTime.parse(value.toString())),
                    cb.lessThanOrEqualTo(key, ZonedDateTime.parse(valueTo.toString()))), predicate);
            }

            return predicate;

        }

    };

    private static final Set<String> FIELD_TYPE = Set.of("Long", "Integer", "Float", "Double", "Short", "Byte");

    public abstract <T> Predicate build(Root<T> root, CriteriaBuilder cb, Predicate predicate,
                                        FilterRequestDTO request);

    public <T, V> Path<V> getPath(Root<T> root, FilterRequestDTO request) {
        String[] keys = request.getKey().split("\\.");
        Path<V> path = root.get(keys[0]);
        for (int i = 1; i < keys.length; i++) {
            path = path.get(keys[i]);
        }
        return path;
    }

}

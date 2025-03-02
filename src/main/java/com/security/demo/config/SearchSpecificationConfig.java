package com.security.demo.config;

import jakarta.persistence.criteria.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.security.demo.dto.FilterRequestDTO;
import com.security.demo.dto.SearchRequestDTO;
import com.security.demo.dto.SortRequestDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchSpecificationConfig<T> implements Specification<T> {

    private static final long serialVersionUID = 1L;

    private final SearchRequestDTO request;

    public SearchSpecificationConfig(SearchRequestDTO request) {
        this.request = request;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Order> orders = new ArrayList<>();
        for (SortRequestDTO sort : this.request.getSorts()) {
            orders.add(sort.getDirection().build(root, cb, sort));
        }

        query.orderBy(orders);

        Predicate predicate = cb.equal(cb.literal(Boolean.TRUE), Boolean.TRUE);
        if (Objects.isNull(this.request.getFilters()) || this.request.getFilters().isEmpty()) return predicate;

        List<FilterRequestDTO> filters = this.request.getFilters();
        predicate = filters.get(0).getOperator().build(root, cb, predicate, filters.get(0));
        for (int index = 1; index < filters.size(); index++) {
            predicate = filters.get(index).getOperator().build(root, cb, predicate, filters.get(index));
        }

        return predicate;
    }

    public Pageable getPageable(Integer page, Integer size) {
        return PageRequest.of(Objects.requireNonNullElse(page, 0), Objects.requireNonNullElse(size, 20));
    }

}

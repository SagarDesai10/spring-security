package com.security.demo.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequestDTO {

    @Valid
    private List<FilterRequestDTO> filters;
    @Valid
    private List<SortRequestDTO> sorts;
    private Integer page;
    private Integer size;

    public List<FilterRequestDTO> getFilters() {
        if (Objects.isNull(this.filters))
            return new ArrayList<>();
        return this.filters;
    }

    public List<SortRequestDTO> getSorts() {
        if (Objects.isNull(this.sorts))
            return new ArrayList<>();
        return this.sorts;
    }
}

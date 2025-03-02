package com.security.demo.dto;

import java.util.List;

import com.security.demo.enums.SearchOperatorEnum;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilterRequestDTO {

    @NotEmpty(message = "Key Should Not Be Null Or Empty")
    private String key;

    @NotNull(message = "Operator Should Not Be Null Or Empty")
    private SearchOperatorEnum operator;

    @NotEmpty(message = "Values Should Not Be Null Or Empty")
    private List<Object> values;

    private String filter;
}

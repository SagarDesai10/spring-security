package com.security.demo.dto;

import com.security.demo.enums.SortDirectionEnum;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SortRequestDTO {

    @NotEmpty(message = "Key Should Not Be Null Or  Empty")
    private String key;

    @NotNull(message = "Direction Should Not Be Null Or Empty")
    private SortDirectionEnum direction;
}

package com.akshay.productservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {

    @NotBlank
    private String name;

    private String description;

    @Positive
    private Double price;

    @Min(0)
    private Integer stockQuantity;

    @NotNull
    private Long categoryId;
}
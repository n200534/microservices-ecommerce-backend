package com.akshay.productservice.dto.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {

    private Long id;

    private String name;

    private String description;

    private Double price;

    private Integer stockQuantity;

    private Long categoryId;

    private String categoryName;
}
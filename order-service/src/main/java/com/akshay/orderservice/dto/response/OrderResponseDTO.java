package com.akshay.orderservice.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderResponseDTO {

    private Long id;

    private Long productId;

    private String productName;

    private Double price;

    private Integer quantity;

    private Double totalAmount;

    private String customerEmail;
}
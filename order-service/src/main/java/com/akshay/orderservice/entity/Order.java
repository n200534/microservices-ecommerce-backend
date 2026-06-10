package com.akshay.orderservice.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;

    private Long productId;

    private String productName;

    private Double price;

    private Integer quantity;

    private Double totalAmount;

    private String customerEmail;
}
package com.akshay.orderservice.service;

import com.akshay.orderservice.dto.request.CreateOrderRequestDTO;
import com.akshay.orderservice.dto.response.OrderResponseDTO;

import java.util.List;

public interface OrderService {

    OrderResponseDTO createOrder(
            CreateOrderRequestDTO request,
            String customerEmail
    );

    OrderResponseDTO getOrderById(
            Long id
    );

    List<OrderResponseDTO> getAllOrders();
}
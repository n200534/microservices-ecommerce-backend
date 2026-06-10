package com.akshay.orderservice.service.impl;

import com.akshay.orderservice.client.ProductClient;
import com.akshay.orderservice.dto.request.CreateOrderRequestDTO;
import com.akshay.orderservice.dto.request.UpdateStockRequestDTO;
import com.akshay.orderservice.dto.response.OrderResponseDTO;
import com.akshay.orderservice.dto.response.ProductResponseDTO;
import com.akshay.orderservice.entity.Order;
import com.akshay.orderservice.exception.InsufficientStockException;
import com.akshay.orderservice.exception.OrderNotFoundException;
import com.akshay.orderservice.exception.ProductNotFoundException;
import com.akshay.orderservice.exception.ProductServiceUnavailableException;
import com.akshay.orderservice.repository.OrderRepository;
import com.akshay.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl
        implements OrderService {

    private final OrderRepository orderRepository;

    private final ProductClient productClient;

    private OrderResponseDTO createOrderFallback(
            CreateOrderRequestDTO request,
            String customerEmail,
            Exception ex
    ) {

        throw new ProductServiceUnavailableException(
                "Product Service is temporarily unavailable"
        );
    }
    @CircuitBreaker(
            name = "productService",
            fallbackMethod = "createOrderFallback"
    )
    @Override
    public OrderResponseDTO createOrder(
            CreateOrderRequestDTO request,
            String customerEmail
    ) {

        ProductResponseDTO product;
        try {

            product = productClient.getProductById(
                    request.getProductId()
            );

        } catch (FeignException.NotFound ex) {

            throw new ProductNotFoundException(
                    "Product not found"
            );
        }

        if (product.getStockQuantity()
                < request.getQuantity()) {

            throw new InsufficientStockException(
                    "Insufficient stock available"
            );
        }

        UpdateStockRequestDTO stockRequest =
                new UpdateStockRequestDTO();

        stockRequest.setQuantity(
                request.getQuantity()
        );

        productClient.reduceStock(
                product.getId(),
                stockRequest
        );

        Double totalAmount =
                product.getPrice()
                        * request.getQuantity();

        Order order = Order.builder()
                .productId(product.getId())
                .productName(product.getName())
                .price(product.getPrice())
                .quantity(
                        request.getQuantity()
                )
                .totalAmount(totalAmount)
                .customerEmail(
                        customerEmail
                )
                .build();

        Order savedOrder =
                orderRepository.save(order);

        return mapToResponse(savedOrder);
    }

    @Override
    public OrderResponseDTO getOrderById(
            Long id
    ) {

        Order order =
                orderRepository.findById(id)
                        .orElseThrow(() ->
                                new OrderNotFoundException(
                                        "Order not found"
                                ));

        return mapToResponse(order);
    }

    @Override
    public List<OrderResponseDTO>
    getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private OrderResponseDTO mapToResponse(
            Order order
    ) {

        return OrderResponseDTO.builder()
                .id(order.getId())
                .productId(
                        order.getProductId()
                )
                .productName(
                        order.getProductName()
                )
                .price(order.getPrice())
                .quantity(
                        order.getQuantity()
                )
                .totalAmount(
                        order.getTotalAmount()
                )
                .customerEmail(
                        order.getCustomerEmail()
                )
                .build();
    }

}
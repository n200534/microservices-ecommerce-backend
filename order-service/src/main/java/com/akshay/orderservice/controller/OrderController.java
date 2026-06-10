package com.akshay.orderservice.controller;

import com.akshay.orderservice.dto.request.CreateOrderRequestDTO;
import com.akshay.orderservice.dto.response.OrderResponseDTO;
import com.akshay.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(
            @Valid @RequestBody CreateOrderRequestDTO request,
            @RequestHeader("X-User-Email")
            String customerEmail
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        orderService.createOrder(
                                request,
                                customerEmail
                        )
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO>
    getOrderById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                orderService.getOrderById(id)
        );
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>>
    getAllOrders() {

        return ResponseEntity.ok(
                orderService.getAllOrders()
        );
    }
}
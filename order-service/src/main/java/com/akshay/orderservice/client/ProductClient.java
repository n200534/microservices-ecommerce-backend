package com.akshay.orderservice.client;

import com.akshay.orderservice.dto.request.UpdateStockRequestDTO;
import com.akshay.orderservice.dto.response.ProductResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "PRODUCT-SERVICE"
)
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductResponseDTO getProductById(
            @PathVariable Long id
    );

    @PutMapping("/products/{id}/stock")
    void reduceStock(
            @PathVariable Long id,
            @RequestBody
            UpdateStockRequestDTO request
    );
}
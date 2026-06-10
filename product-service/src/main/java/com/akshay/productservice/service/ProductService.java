package com.akshay.productservice.service;

import com.akshay.productservice.dto.request.ProductRequestDTO;
import com.akshay.productservice.dto.response.ProductResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {

    ProductResponseDTO createProduct(
            ProductRequestDTO request
    );

    ProductResponseDTO getProductById(Long id);

    List<ProductResponseDTO> getAllProducts();

    List<ProductResponseDTO> getProductsByCategory(
            Long categoryId
    );

    List<ProductResponseDTO> searchProducts(
            String keyword
    );

    Page<ProductResponseDTO> getAllProducts(
            int page,
            int size
    );

    void deleteProduct(Long id);

    void reduceStock(
            Long productId,
            Integer quantity
    );
}
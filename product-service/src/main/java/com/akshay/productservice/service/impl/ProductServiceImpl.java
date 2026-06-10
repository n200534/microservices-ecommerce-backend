package com.akshay.productservice.service.impl;

import com.akshay.productservice.dto.request.ProductRequestDTO;
import com.akshay.productservice.dto.response.ProductResponseDTO;
import com.akshay.productservice.entity.Category;
import com.akshay.productservice.entity.Product;
import com.akshay.productservice.exception.InsufficientStockException;
import com.akshay.productservice.exception.ProductNotFoundException;
import com.akshay.productservice.exception.ResourceNotFoundException;
import com.akshay.productservice.repository.CategoryRepository;
import com.akshay.productservice.repository.ProductRepository;
import com.akshay.productservice.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    @Override
    public ProductResponseDTO createProduct(
            ProductRequestDTO request
    ) {

        Category category = categoryRepository.findById(
                        request.getCategoryId()
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category not found with id: "
                                        + request.getCategoryId()
                        ));

        Product product = Product.builder()
                .name(request.getName())
                .description(request.getDescription())
                .price(request.getPrice())
                .stockQuantity(request.getStockQuantity())
                .category(category)
                .build();

        Product saved = productRepository.save(product);

        return mapToResponse(saved);
    }

    @Override
    public ProductResponseDTO getProductById(Long id) {

        Product product =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product not found with id: "
                                                + id
                                ));

        return mapToResponse(product);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ProductResponseDTO> getProductsByCategory(
            Long categoryId
    ) {

        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException(
                    "Category not found with id: "
                            + categoryId
            );
        }

        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ProductResponseDTO> searchProducts(
            String keyword
    ) {

        return productRepository
                .findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    @Override
    public void deleteProduct(Long id) {

        Product product =
                productRepository.findById(id)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product not found with id: "
                                                + id
                                ));

        productRepository.delete(product);
    }

    @Override
    public Page<ProductResponseDTO>
    getAllProducts(
            int page,
            int size
    ) {

        Pageable pageable =
                PageRequest.of(page, size);

        return productRepository
                .findAll(pageable)
                .map(this::mapToResponse);
    }

    private ProductResponseDTO mapToResponse(
            Product product
    ) {

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .build();
    }

    @Override
    public void reduceStock(
            Long productId,
            Integer quantity
    ) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product not found with id: "
                                                + productId
                                ));

        if(product.getStockQuantity()
                < quantity) {

            throw new InsufficientStockException(
                    "Insufficient stock available"
            );
        }

        product.setStockQuantity(
                product.getStockQuantity()
                        - quantity
        );

        productRepository.save(product);
    }
}
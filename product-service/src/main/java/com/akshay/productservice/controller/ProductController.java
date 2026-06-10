package com.akshay.productservice.controller;

import com.akshay.productservice.dto.request.ProductRequestDTO;
import com.akshay.productservice.dto.request.UpdateStockRequestDTO;
import com.akshay.productservice.dto.response.ProductResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Product APIs",
        description = "Manage Products"
)
@RestController
@RequestMapping("/products")
public class ProductController {

    private final com.akshay.productservice.service.ProductService productService;

    public ProductController(com.akshay.productservice.service.ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponseDTO>
    createProduct(
            @Valid
            @RequestBody
            ProductRequestDTO request
    ) {

        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(
                productService.createProduct(
                        request
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO>
    getProductById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                productService.getProductById(id)
        );
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>>
    getAllProducts(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return ResponseEntity.ok(
                productService.getAllProducts(
                        page,
                        size
                )
        );
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ProductResponseDTO>>
    getProductsByCategory(
            @PathVariable Long categoryId
    ) {

        return ResponseEntity.ok(
                productService.getProductsByCategory(
                        categoryId
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDTO>>
    searchProducts(
            @RequestParam String keyword
    ) {

        return ResponseEntity.ok(
                productService.searchProducts(
                        keyword
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteProduct(
            @PathVariable Long id
    ) {

        productService.deleteProduct(id);

        return ResponseEntity.noContent()
                .build();
    }

    @PutMapping("/{id}/stock")
    public ResponseEntity<Void> reduceStock(
            @PathVariable Long id,
            @RequestBody UpdateStockRequestDTO request
    ) {

        productService.reduceStock(
                id,
                request.getQuantity()
        );

        return ResponseEntity.ok()
                .build();
    }
}
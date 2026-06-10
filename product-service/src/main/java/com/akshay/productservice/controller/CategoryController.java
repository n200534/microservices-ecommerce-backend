package com.akshay.productservice.controller;

import com.akshay.productservice.dto.request.CategoryRequestDTO;
import com.akshay.productservice.dto.response.CategoryResponseDTO;
import com.akshay.productservice.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO>
    createCategory(
            @Valid
            @RequestBody
            CategoryRequestDTO request
    ) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        categoryService.createCategory(
                                request
                        )
                );
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDTO>>
    getAllCategories() {

        return ResponseEntity.ok(
                categoryService.getAllCategories()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO>
    getCategoryById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                categoryService.getCategoryById(id)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @PathVariable Long id
    ) {

        categoryService.deleteCategory(id);

        return ResponseEntity.noContent()
                .build();
    }
}
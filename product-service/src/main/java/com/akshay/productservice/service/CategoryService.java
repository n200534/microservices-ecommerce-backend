package com.akshay.productservice.service;

import com.akshay.productservice.dto.request.CategoryRequestDTO;
import com.akshay.productservice.dto.request.CategoryRequestDTO;
import com.akshay.productservice.dto.response.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {

    CategoryResponseDTO createCategory(
            CategoryRequestDTO request
    );

    List<CategoryResponseDTO> getAllCategories();

    CategoryResponseDTO getCategoryById(Long id);

    void deleteCategory(Long id);
}
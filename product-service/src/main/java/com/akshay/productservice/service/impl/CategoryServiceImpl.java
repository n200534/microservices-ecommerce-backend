package com.akshay.productservice.service.impl;

import com.akshay.productservice.dto.request.CategoryRequestDTO;
import com.akshay.productservice.dto.response.CategoryResponseDTO;
import com.akshay.productservice.entity.Category;
import com.akshay.productservice.repository.CategoryRepository;
import com.akshay.productservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl
        implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponseDTO createCategory(
            CategoryRequestDTO request
    ) {

        if (categoryRepository.findByName(
                request.getName()
        ).isPresent()) {

            throw new RuntimeException(
                    "Category already exists"
            );
        }

        Category category = Category.builder()
                .name(request.getName())
                .build();

        Category saved =
                categoryRepository.save(category);

        return CategoryResponseDTO.builder()
                .id(saved.getId())
                .name(saved.getName())
                .build();
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(category ->
                        CategoryResponseDTO.builder()
                                .id(category.getId())
                                .name(category.getName())
                                .build())
                .toList();
    }

    @Override
    public CategoryResponseDTO getCategoryById(Long id) {

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Category not found"));

        return CategoryResponseDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    @Override
    public void deleteCategory(Long id) {

        Category category =
                categoryRepository.findById(id)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Category not found"));

        categoryRepository.delete(category);
    }
}
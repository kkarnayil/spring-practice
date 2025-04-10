package com.kartiktest.practice.service;

import com.kartiktest.practice.dto.CategoryDTO;
import com.kartiktest.practice.dto.CategoryResponse;
import jakarta.validation.Valid;

public interface CategoryService {

  CategoryResponse getCategories(
      Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

  CategoryDTO createCategory(final @Valid CategoryDTO category);

  CategoryDTO updateCategory(final CategoryDTO category, long categoryId);

  CategoryDTO deleteCategory(final long categoryId);
}

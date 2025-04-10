package com.kartiktest.practice.service;

import com.kartiktest.practice.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();
    List<Category> createCategory(final Category category);
    String updateCategory(final Category category, long categoryId);
    String deleteCategory(final long categoryId);
}

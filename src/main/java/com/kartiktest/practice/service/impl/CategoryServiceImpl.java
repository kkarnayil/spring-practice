package com.kartiktest.practice.service.impl;

import com.kartiktest.practice.model.Category;
import com.kartiktest.practice.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final List<Category> categories = new ArrayList<>();
  private int id = 1;

  @Override
  public List<Category> getCategories() {
    return categories;
  }

  @Override
  public List<Category> createCategory(final Category category) {
    category.setId(id++);
    categories.add(category);
    return categories;
  }

  @Override
  public String updateCategory(final Category category, long categoryId) {

    final Category filteredCategory = categories.stream()
        .filter(item -> item.getId() == categoryId)
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

    filteredCategory.setName(category.getName());
    filteredCategory.setDescription(category.getDescription());
    return "Category updated: "+filteredCategory.getId();

  }

  @Override
  public String deleteCategory(final long categoryId) {

    final Category category =
        categories.stream()
            .filter(categoryItem -> categoryItem.getId() == categoryId)
            .findFirst()
            .orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

    categories.remove(category);

    return "Category " + categoryId + " deleted";
  }
}

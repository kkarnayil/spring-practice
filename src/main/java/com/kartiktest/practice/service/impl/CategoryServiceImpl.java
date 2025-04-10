package com.kartiktest.practice.service.impl;

import com.kartiktest.practice.model.Category;
import com.kartiktest.practice.repositorties.CategoryRepository;
import com.kartiktest.practice.service.CategoryService;
import java.util.List;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  public List<Category> getCategories() {
    return categoryRepository.findAll();
  }

  @Override
  public List<Category> createCategory(final Category category) {
    categoryRepository.save(category);
    return categoryRepository.findAll();
  }

  @Override
  public String updateCategory(final Category category, long categoryId) {

    final Category filteredCategory = getCategory(categoryId);
    category.setId(filteredCategory.getId());
    categoryRepository.save(category);
    return "Category updated: " + filteredCategory.getId();
  }

  @Override
  public String deleteCategory(final long categoryId) {

    final Category category = getCategory(categoryId);

    categoryRepository.delete(category);

    return "Category " + categoryId + " deleted";
  }

  private Category getCategory(final long categoryId) {
      return categoryRepository
          .findById(categoryId)
          .orElseThrow(
              () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
  }
}

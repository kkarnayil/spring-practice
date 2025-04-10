package com.kartiktest.practice.service.impl;

import com.kartiktest.practice.exceptions.ApiException;
import com.kartiktest.practice.exceptions.ResourceNotFoundException;
import com.kartiktest.practice.model.Category;
import com.kartiktest.practice.repositorties.CategoryRepository;
import com.kartiktest.practice.service.CategoryService;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  @Override
  public List<Category> getCategories() {
    log.debug("getCategories");
    final long categoryCount = categoryRepository.count();
    if (categoryCount == 0) throw new ApiException("No Category Found");
    return categoryRepository.findAll();
  }

  @Override
  public String createCategory(final Category category) {
    try {
      log.debug("createCategory : {}", category.getName());
      final Category savedCategory = categoryRepository.findByName(category.getName());

      if (savedCategory != null)
        throw new ApiException(
            String.format("Category with %s already exists", category.getName()));

      categoryRepository.save(category);
    } catch (ConstraintViolationException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Constraint violation");
    }
    return "Category with name " + category.getName() + " created";
  }

  @Override
  public String updateCategory(final Category category, long categoryId) {
    log.debug("updateCategory : {}", categoryId);
    final Category filteredCategory = getCategory(categoryId);
    category.setId(filteredCategory.getId());
    categoryRepository.save(category);
    return "Category updated: " + filteredCategory.getId();
  }

  @Override
  public String deleteCategory(final long categoryId) {
    log.debug("deleteCategory : {}", categoryId);
    final Category category = getCategory(categoryId);
    categoryRepository.delete(category);
    return "Category with Id: " + categoryId + " deleted!";
  }

  private Category getCategory(final long categoryId) {
    log.debug("getCategory : {}", categoryId);
    return categoryRepository
        .findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category", categoryId));
  }
}

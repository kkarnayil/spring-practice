package com.kartiktest.practice.controllers;

import com.kartiktest.practice.common.AppConstants;
import com.kartiktest.practice.dto.CategoryDTO;
import com.kartiktest.practice.dto.CategoryResponse;
import com.kartiktest.practice.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("/public/v1/categories")
  public ResponseEntity<CategoryResponse> getCategories(
      @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER_DEFAULT)
          Integer pageNumber,
      @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE_DEFAULT)
          Integer pageSize,
      @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_ORDER_BY_DEFAULT)
          String sortBy,
      @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_ORDER_DEFAULT)
          String sortOrder) {

    return ResponseEntity.ok(
        categoryService.getCategories(pageNumber, pageSize, sortBy, sortOrder));
  }

  @PostMapping("/public/v1/categories")
  public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO category) {
    return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(category));
  }

  @PutMapping("/public/v1/categories/{categoryId}")
  public ResponseEntity<CategoryDTO> updateCategory(
      @RequestBody CategoryDTO category, @PathVariable long categoryId) {
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(categoryService.updateCategory(category, categoryId));
  }

  @DeleteMapping("/admin/v1/categories/{categoryId}")
  public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable long categoryId) {
    return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
  }
}

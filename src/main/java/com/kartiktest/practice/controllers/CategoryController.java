package com.kartiktest.practice.controllers;

import com.kartiktest.practice.model.Category;
import com.kartiktest.practice.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CategoryController {

  private final CategoryService categoryService;

  public CategoryController(CategoryService categoryService) {
    this.categoryService = categoryService;
  }

  @GetMapping("/api/public/v1/categories")
  public ResponseEntity<List<Category>> getCategories() {
    return ResponseEntity.ok(categoryService.getCategories());
  }

  @PostMapping("/api/public/v1/categories")
  public ResponseEntity<String> addCategory(@Valid @RequestBody Category category) {
    return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(category));
  }

  @PutMapping("/api/public/v1/categories/{categoryId}")
  public ResponseEntity<String> updateCategory(
      @RequestBody Category category, @PathVariable long categoryId) {
    return ResponseEntity.status(HttpStatus.ACCEPTED)
        .body(categoryService.updateCategory(category, categoryId));
  }

  @DeleteMapping("/api/admin/v1/categories/{categoryId}")
  public ResponseEntity<String> deleteCategory(@PathVariable long categoryId) {
    return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
  }
}

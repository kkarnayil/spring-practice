package com.kartiktest.practice.controllers;

import com.kartiktest.practice.model.Category;
import com.kartiktest.practice.service.CategoryService;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
  public ResponseEntity<List<Category>> addCategory(@RequestBody Category category) {
    return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.createCategory(category));
  }

  @PutMapping("/api/public/v1/categories/{categoryId}")
  public ResponseEntity<String> updateCategory(
      @RequestBody Category category, @PathVariable long categoryId) {
    try {
      return ResponseEntity.status(HttpStatus.ACCEPTED)
          .body(categoryService.updateCategory(category, categoryId));
    } catch (ResponseStatusException e) {
      return new ResponseEntity<>(e.getReason(), e.getStatusCode());
    }
  }

  @DeleteMapping("/api/admin/v1/categories/{categoryId}")
  public ResponseEntity<String> deleteCategory(@PathVariable long categoryId) {
    try {
      return ResponseEntity.ok(categoryService.deleteCategory(categoryId));
    } catch (ResponseStatusException e) {
      return new ResponseEntity<>(e.getReason(), e.getStatusCode());
    }
  }
}

package com.kartiktest.practice.service.impl;

import com.kartiktest.practice.dto.CategoryDTO;
import com.kartiktest.practice.dto.CategoryResponse;
import com.kartiktest.practice.exceptions.ApiException;
import com.kartiktest.practice.exceptions.ResourceNotFoundException;
import com.kartiktest.practice.model.Category;
import com.kartiktest.practice.repositorties.CategoryRepository;
import com.kartiktest.practice.service.CategoryService;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;

  private final ModelMapper modelMapper;

  @Override
  public CategoryResponse getCategories(
      Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
    log.debug("Method getCategories invoked");
    final long categoryCount = categoryRepository.count();
    if (categoryCount == 0) throw new ApiException("No Category Found");

    if (pageNumber < 1) pageNumber = 1;

    final Sort sortByQuery =
        sortOrder.equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

    final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sortByQuery);
    final Page<Category> categoryPage = categoryRepository.findAll(pageable);

    final List<CategoryDTO> categories =
        categoryPage.getContent().stream()
            .map(category -> modelMapper.map(category, CategoryDTO.class))
            .toList();

    final CategoryResponse categoryResponse = new CategoryResponse();
    categoryResponse.setContent(categories);
    categoryResponse.setTotal(categoryPage.getTotalElements());
    categoryResponse.setTotalPage(categoryPage.getTotalPages());
    categoryResponse.setCurrentPage(pageNumber);
    categoryResponse.setLastPage(categoryPage.isLast());

    return categoryResponse;
  }

  @Override
  public CategoryDTO createCategory(final CategoryDTO category) {
    log.debug("invoked method createCategory : {}", category.getName());

    Category savedCategory = categoryRepository.findByName(category.getName());
    if (savedCategory != null)
      throw new ApiException(String.format("Category with %s already exists", category.getName()));

    Category categoryModel = modelMapper.map(category, Category.class);
    categoryRepository.save(categoryModel);

    savedCategory = categoryRepository.save(categoryModel);
    return modelMapper.map(savedCategory, CategoryDTO.class);
  }

  @Override
  public CategoryDTO updateCategory(final CategoryDTO category, long categoryId) {
    log.debug("invoked method updateCategory : {}", categoryId);
    final Category filteredCategory = getCategory(categoryId);
    final Category categoryModel = modelMapper.map(category, Category.class);
    categoryModel.setId(filteredCategory.getId());
    final Category updatedCategory = categoryRepository.save(categoryModel);
    return modelMapper.map(updatedCategory, CategoryDTO.class);
  }

  @Override
  public CategoryDTO deleteCategory(final long categoryId) {
    log.debug("invoked method deleteCategory : {}", categoryId);
    final Category category = getCategory(categoryId);
    categoryRepository.delete(category);

    return modelMapper.map(category, CategoryDTO.class);
  }

  private Category getCategory(final long categoryId) {
    log.debug("invoked method getCategory : {}", categoryId);
    return categoryRepository
        .findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category", categoryId));
  }
}

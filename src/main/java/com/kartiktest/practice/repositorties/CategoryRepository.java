package com.kartiktest.practice.repositorties;

import com.kartiktest.practice.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
  Category findByName(String name);
}

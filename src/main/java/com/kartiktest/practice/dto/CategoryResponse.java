package com.kartiktest.practice.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {
  private long total;
  private int currentPage;
  private int totalPage;
  private boolean isLastPage;
  private List<CategoryDTO> content;
}

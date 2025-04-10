package com.kartiktest.practice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @NotBlank
  @Size(min = 5, message = "Category name should contain atleast 5 characters.")
  private String name;

  private String description;
}

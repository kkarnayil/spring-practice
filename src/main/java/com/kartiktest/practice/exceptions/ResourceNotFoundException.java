package com.kartiktest.practice.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ResourceNotFoundException extends RuntimeException {
  private String field;
  private long fieldId;

  public ResourceNotFoundException(final String field, final long fieldId) {
    super(String.format("%s not found with fieldId: %d", field, fieldId));
    this.field = field;
    this.fieldId = fieldId;
  }

  public ResourceNotFoundException(final String field, final String fieldId) {
    super(String.format("%s not found with fieldId: %s", field, fieldId));
    this.field = field;
    this.fieldId = Long.parseLong(fieldId);
  }
}

package com.kartiktest.practice.exceptions;

import com.kartiktest.practice.dto.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> myMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {

    final Map<String, String> response = new HashMap<>();

    e.getBindingResult()
        .getAllErrors()
        .forEach(
            (error) -> {
              String fieldName = ((FieldError) error).getField();
              String message = error.getDefaultMessage();
              response.put(fieldName, message);
            });

    log.error(e.getMessage());
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Map<String, String>> myConstraintViolationException(
      ConstraintViolationException e) {

    final Map<String, String> response = new HashMap<>();

    e.getConstraintViolations()
        .forEach(
            constraintViolation ->
                response.put(
                    constraintViolation.getPropertyPath().toString(),
                    constraintViolation.getMessage()));
    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<ApiErrorResponse> resourceNotFoundHandler(ResourceNotFoundException e) {
    final String message = e.getMessage();
    log.error(e.getMessage());
    ApiErrorResponse apiErrorResponse = new ApiErrorResponse(message, false);
    return new ResponseEntity<>(apiErrorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<ApiErrorResponse> apiException(ApiException e) {
    final String message = e.getMessage();
    log.error(e.getMessage());
    ApiErrorResponse apiErrorResponse = new ApiErrorResponse(message, false);
    return new ResponseEntity<>(apiErrorResponse, HttpStatus.BAD_REQUEST);
  }
}

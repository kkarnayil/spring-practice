package com.kartiktest.practice.controllers;

public class HelloResponse {
  private String message;

  public HelloResponse(String message) {
    this.message = message;
  }

  public String getMessage() {
    return "Hello, " + message +" !";
  }

  public void setMessage(String message) {
    this.message = message;
  }
}

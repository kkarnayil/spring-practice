package com.kartiktest.practice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProductController {

  @RequestMapping(value = "/public/v1/products", method = RequestMethod.GET)
  public String getProducts() {
    return "Product List";
  }

}

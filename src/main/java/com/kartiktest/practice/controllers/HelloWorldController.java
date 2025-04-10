package com.kartiktest.practice.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
public class HelloWorldController {

  @GetMapping("/hello")
  public String sayHello() {
    return "Hello World";
  }
  
  @GetMapping("/hello/{name}")
  public HelloResponse sayHelloParam(@PathVariable String name) {
    return new HelloResponse(name);
  }

  @PostMapping("/hello")
  public HelloResponse sayHelloPost(@RequestBody String body) {
    return new HelloResponse(body);
  }
}

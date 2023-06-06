package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.repositories.ProductRepository;
import com.example.springsecurityapplication.services.ProductService;
import com.example.springsecurityapplication.models.Product;
import com.example.springsecurityapplication.repositories.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {

  private  final ProductService productService;

  public AuthenticationController(ProductRepository productRepository, ProductService productService) {
    this.productService = productService;
}

  @GetMapping("/authentication")
  public String login(Model model){
    model.addAttribute("products", productService.getAllProduct());
    return "authentication";
  }

}

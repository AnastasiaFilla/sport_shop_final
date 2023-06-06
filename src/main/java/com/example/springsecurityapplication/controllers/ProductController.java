package com.example.springsecurityapplication.controllers;

import com.example.springsecurityapplication.repositories.ProductRepository;
import com.example.springsecurityapplication.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
@RequestMapping("/product")
public class ProductController {

    private  final ProductService productService;

    public ProductController(ProductRepository productRepository, ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public String getAllProduct(Model model){
        model.addAttribute("products", productService.getAllProduct());
        return "product/product";
    }

    @GetMapping("/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model){
        model.addAttribute("product", productService.getProductId(id));
        return "product/infoProduct";
    }

    @PostMapping("/search")
    public String productSearch(@RequestParam("search") String search, @RequestParam("ot") String ot, @RequestParam("do") String Do, @RequestParam(value = "price", required = false, defaultValue = "") String price, @RequestParam(value = "contract", required = false, defaultValue = "")String contract, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        model.addAttribute("products", productService.getAllProduct());
        model.addAttribute("search_product", productService.getSearchProduct(search, ot, Do, price, contract, model));
        model.addAttribute("value_search", search);
        model.addAttribute("value_price_ot", ot);
        model.addAttribute("value_price_do", Do);

        return "authentication";
    }
}

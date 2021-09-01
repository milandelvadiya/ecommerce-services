package com.ecommerce.EcommerceDemo.controller;

import com.ecommerce.EcommerceDemo.entity.Product;
import com.ecommerce.EcommerceDemo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/api/products")
    public List<Product> getProducts(@RequestParam Optional<String> category){
        return productService.fetchProducts(category);
    }

    @PostMapping("/api/products")
    public Integer addProduct(@RequestBody Product product){
        return productService.addProduct(product);
    }


}

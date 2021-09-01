package com.ecommerce.EcommerceDemo.service;

import com.ecommerce.EcommerceDemo.entity.Product;
import com.ecommerce.EcommerceDemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<Product> fetchProducts(Optional<String> category){
        if(category.isPresent()){
            return repository.findByCategory(category.get());
        }
        return repository.findAll();
    }

    public Integer addProduct(Product product) {
        return repository.save(product).getId();
    }
}

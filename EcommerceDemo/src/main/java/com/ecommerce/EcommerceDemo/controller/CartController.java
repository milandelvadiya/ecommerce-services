package com.ecommerce.EcommerceDemo.controller;

import com.ecommerce.EcommerceDemo.dto.CartDTO;
import com.ecommerce.EcommerceDemo.entity.Cart;
import com.ecommerce.EcommerceDemo.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/api/cart")
    public Cart getCart(){
        return cartService.fetchCart();
    }

    @PostMapping("/api/cart")
    public Cart addProductToCart(@RequestBody CartDTO cartDTO){
        return cartService.addProductToCart(cartDTO);
    }



}

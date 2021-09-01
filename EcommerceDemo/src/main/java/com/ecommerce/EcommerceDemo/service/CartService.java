package com.ecommerce.EcommerceDemo.service;

import com.ecommerce.EcommerceDemo.dto.CartDTO;
import com.ecommerce.EcommerceDemo.entity.Cart;
import com.ecommerce.EcommerceDemo.entity.CartItem;
import com.ecommerce.EcommerceDemo.entity.Product;
import com.ecommerce.EcommerceDemo.repository.CartRepository;
import com.ecommerce.EcommerceDemo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    public Cart fetchCart() {
        Optional<Cart> optionalCart = cartRepository.findByActiveTrue();
        if(optionalCart.isPresent()){
            return optionalCart.get();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart is empty");
    }

    public Cart addProductToCart(CartDTO cartDTO) {
        Optional<Product> optionalProduct = productRepository.findById(cartDTO.getProductId());
        if(optionalProduct.isPresent()){
            Product product = optionalProduct.get();
            Cart cart;
            CartItem cartItem = CartItem.builder()
                    .product(product)
                    .price(product.getPrice())
                    .quantity(cartDTO.getQuantity())
                    .build();
            Optional<Cart> optionalCart = cartRepository.findByActiveTrue();
            if(optionalCart.isPresent()){
                cart = optionalCart.get();
                cart.getItems().add(cartItem);
                cart.setCartAmount(calculateCartAmount(cart.getItems()));
            } else {
                List<CartItem> cartItems = Arrays.asList(cartItem);
                cart = Cart.builder()
                        .items(cartItems)
                        .cartAmount(calculateCartAmount(cartItems))
                        .active(true)
                        .build();
            }
            return cartRepository.save(cart);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product does not exist");
        }

    }

    private Integer calculateCartAmount(List<CartItem> items) {
        return items.stream().mapToInt(cartItem -> cartItem.getPrice()*cartItem.getQuantity()).sum();
    }
}

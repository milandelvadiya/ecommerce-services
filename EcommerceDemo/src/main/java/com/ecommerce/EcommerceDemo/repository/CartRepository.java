package com.ecommerce.EcommerceDemo.repository;

import com.ecommerce.EcommerceDemo.entity.Cart;
import com.ecommerce.EcommerceDemo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByActiveTrue();

}

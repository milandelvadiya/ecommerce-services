package com.ecommerce.EcommerceDemo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
//    @ManyToOne
//    @JoinColumn(name = "cart_id")
//    private Cart cart;
    @OneToOne()
    private Product product;
    private Integer price;
    private Integer quantity;
}

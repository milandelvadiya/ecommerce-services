package com.ecommerce.EcommerceDemo.controller;

import com.ecommerce.EcommerceDemo.entity.SalesOrder;
import com.ecommerce.EcommerceDemo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/api/orders")
    public List<SalesOrder> getOrders(@RequestParam Optional<Integer> orderId) {
        return orderService.fetchOrders(orderId);
    }

    @PostMapping("/api/orders")
    public Integer placeOrder() {
        return orderService.placeOrder();
    }

    @PostMapping("/api/orders/{orderId}/payment")
    public String makePayment(@PathVariable("orderId") Integer orderId) {
        orderService.updatePayment(orderId);
        return "Payment completed";
    }


}

package com.ecommerce.EcommerceDemo.service;

import com.ecommerce.EcommerceDemo.entity.Cart;
import com.ecommerce.EcommerceDemo.entity.CartItem;
import com.ecommerce.EcommerceDemo.entity.OrderItem;
import com.ecommerce.EcommerceDemo.entity.SalesOrder;
import com.ecommerce.EcommerceDemo.repository.CartRepository;
import com.ecommerce.EcommerceDemo.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    public List<SalesOrder> fetchOrders(Optional<Integer> optionalOrderId) {
        if(optionalOrderId.isPresent()){
            Optional<SalesOrder> optionalSalesOrder = orderRepository.findById(optionalOrderId.get());
            if(optionalSalesOrder.isPresent()){
                return Arrays.asList(optionalSalesOrder.get());
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found");
            }
        } else {
            return orderRepository.findAll();
        }
    }

    public Integer placeOrder() {
        SalesOrder salesOrder;
        Optional<Cart> optionalCart = cartRepository.findByActiveTrue();
        if (optionalCart.isPresent()) {
            Cart cart = optionalCart.get();
            List<OrderItem> orderItems = cart.getItems().stream().map(this::mapOrderItem).collect(Collectors.toList());
            salesOrder = SalesOrder.builder()
                    .orderItems(orderItems)
                    .orderStatus("NEW")
                    .paymentStatus("PENDING")
                    .orderAmount(calculateOrderAmount(orderItems))
                    .build();
            salesOrder = orderRepository.save(salesOrder);
            cart.setActive(false);
            cartRepository.save(cart);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cart is empty");
        }
        return salesOrder.getId();
    }

    private OrderItem mapOrderItem(CartItem cartItem) {
        return OrderItem.builder()
                .price(cartItem.getPrice())
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .build();
    }

    private Integer calculateOrderAmount(List<OrderItem> items) {
        return items.stream().mapToInt(orderItem -> orderItem.getPrice() * orderItem.getQuantity()).sum();
    }

    public void updatePayment(Integer orderId) {
        Optional<SalesOrder> optionalSalesOrder = orderRepository.findById(orderId);
        if(optionalSalesOrder.isPresent()){
            SalesOrder salesOrder = optionalSalesOrder.get();
            salesOrder.setPaymentStatus("COMPLETED");
            salesOrder.setOrderStatus("INPROGRESS");
            orderRepository.save(salesOrder);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No order found with orderId="+orderId);
        }
    }
}

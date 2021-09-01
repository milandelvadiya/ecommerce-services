package com.ecommerce.EcommerceDemo.repository;

import com.ecommerce.EcommerceDemo.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<SalesOrder, Integer> {

}

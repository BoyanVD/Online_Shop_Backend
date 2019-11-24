package com.shop.demo.repository;

import com.shop.demo.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface OrderRepository extends JpaRepository<Order, Long> {

  public Set<Order> findOrdersByUserId(Long id);
}

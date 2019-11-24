package com.shop.demo.repository;

import com.shop.demo.model.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketRepository extends JpaRepository<Basket, Long> {

  public Basket findBasketByUserId(Long id);
}

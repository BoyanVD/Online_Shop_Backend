package com.shop.demo.repository;

import com.shop.demo.model.entity.BasketProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BasketProductRepository extends JpaRepository<BasketProduct, Long> {

  public List<BasketProduct> findBasketProductByBasketUserId(Long id);

  public BasketProduct findBasketProductByBasket_IdAndAndProduct_Id(Long basketId, Long productId);

  public void deleteBasketProductsByProduct_Id(Long productId);
}

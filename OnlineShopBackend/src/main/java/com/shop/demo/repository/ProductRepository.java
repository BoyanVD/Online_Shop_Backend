package com.shop.demo.repository;

import com.shop.demo.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

  public List<Product> findAll();

  public Product findProductById(Long id);

  public List<Product> findProductsBySellerId(Long sellerId);

  public List<Product> findProductsByIdIn(List<Long> listOfProductIds);

  public Product findProductBySeller_IdAndAndName(Long sellerId, String name);

  public Product findProductByIdAndSeller_Id(Long productId, Long sellerId);

  public Long deleteByIdAndSeller_Id(Long productId, Long sellerId);
}

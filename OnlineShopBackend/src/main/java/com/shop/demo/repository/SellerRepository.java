package com.shop.demo.repository;

import com.shop.demo.model.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface SellerRepository extends JpaRepository<Seller, Long> {

  public Set<Seller> findSellersByUserId(Long id);

  public Seller findSellerById(long id);

  public Seller findSellerByBusinessDisplayName(String businessDislplayName);
}

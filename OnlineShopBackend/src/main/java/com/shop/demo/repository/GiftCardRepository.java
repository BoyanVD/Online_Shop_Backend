package com.shop.demo.repository;

import com.shop.demo.model.entity.GiftCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface GiftCardRepository extends JpaRepository<GiftCard, Long> {

  public Set<GiftCard> findGiftCardsByUserId(Long id);
}

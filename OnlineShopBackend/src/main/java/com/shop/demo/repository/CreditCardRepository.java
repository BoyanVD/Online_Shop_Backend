package com.shop.demo.repository;

import com.shop.demo.model.entity.CreditCard;
import com.shop.demo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

  public Set<CreditCard> findCreditCardsByUsersId(long userId);

  public CreditCard findCreditCardById(Long id);

  public CreditCard findCreditCardByCreditCardNumber(String creditCardNumber);

}

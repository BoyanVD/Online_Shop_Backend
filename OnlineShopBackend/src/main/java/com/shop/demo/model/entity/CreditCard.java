package com.shop.demo.model.entity;

import com.shop.demo.model.dto.AddCreditCardDTO;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "credit_card")
public class CreditCard {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "credit_card_number")
  private String creditCardNumber;

  @Column(name = "expiring_date")
  private LocalDate expiringDate;

  @Column(name = "card_holder_name")
  private String cardHolderName;

  @Column(name = "balance")
  private double balance;

  @EqualsAndHashCode.Exclude
  @ManyToMany
  private Set<User> users;

  public CreditCard(AddCreditCardDTO creditCard){

    this.creditCardNumber = creditCard.getCreditCardNumber();
    this.expiringDate = creditCard.getExpiringDate();
    this.cardHolderName = creditCard.getCardHolderName();
    this.balance = creditCard.getBalance();
  }
}

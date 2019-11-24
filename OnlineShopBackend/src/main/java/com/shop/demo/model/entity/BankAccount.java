package com.shop.demo.model.entity;

import com.shop.demo.model.dto.BankAccountRegistrationDTO;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "bank_account")
public class BankAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "iban")
  private String iban;

  @Column(name = "balance")
  private double balance;

  @EqualsAndHashCode.Exclude
  @ManyToOne
  @JoinColumn(name = "seller_id")
  private Seller seller;

  public BankAccount(BankAccountRegistrationDTO bankAccountRegistrationDTO){
    this.iban = bankAccountRegistrationDTO.getIban();
    this.balance = bankAccountRegistrationDTO.getBalance();
  }
}

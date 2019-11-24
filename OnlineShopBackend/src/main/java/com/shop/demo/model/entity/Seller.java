package com.shop.demo.model.entity;

import com.shop.demo.model.dto.SellerRegistrationDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seller")
public class Seller {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String legalName;
  private String businessDisplayName;

  @EqualsAndHashCode.Exclude
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "seller")
  private Set<BankAccount> bankAccounts;

  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "seller")
  private Set<Product> products;

  public Seller(SellerRegistrationDTO seller, User user){
    this.legalName = seller.getLegalName();
    this.businessDisplayName = seller.getBusinessDisplayName();
    this.user = user;
  }
}

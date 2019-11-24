package com.shop.demo.model.entity;

import com.shop.demo.model.dto.UserRegistrationDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "basket")
public class Basket {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @EqualsAndHashCode.Exclude
  @OneToOne
  private User user;

  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "basket")
  private Set<BasketProduct> basketProducts;

  public Basket(User user){
    this.user = user;
  }
}

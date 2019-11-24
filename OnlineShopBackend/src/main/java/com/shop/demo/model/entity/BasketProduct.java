package com.shop.demo.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "basket_product")
public class BasketProduct {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "quantity")
  private int quantity;

  @EqualsAndHashCode.Exclude
  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @EqualsAndHashCode.Exclude
  @ManyToOne
  @JoinColumn(name = "basket_id")
  private Basket basket;

}

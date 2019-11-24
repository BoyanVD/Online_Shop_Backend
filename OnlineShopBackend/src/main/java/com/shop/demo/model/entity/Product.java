package com.shop.demo.model.entity;

import com.shop.demo.model.dto.ProductAddToSellDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private String description;
  private Double price;
  private Integer quantity;

  @EqualsAndHashCode.Exclude
  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @EqualsAndHashCode.Exclude
  @ManyToOne
  @JoinColumn(name = "seller_id")
  private Seller seller;

  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "product")
  private Set<BasketProduct> productInBaskets;

  public Product(ProductAddToSellDTO product, Category category, Seller seller){

    this.name = product.getName();
    this.description = product.getDescription();
    this.price = product.getPrice();
    this.category = category;
    this.seller = seller;
  }
}

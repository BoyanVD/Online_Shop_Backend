package com.shop.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "category")
public class Category {

  @Id
  @GeneratedValue(strategy= GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @JsonIgnore
  @EqualsAndHashCode.Exclude
  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "parent_category_id")
  private Category parentCategory;

  @JsonIgnore
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "parentCategory")
  private Set<Category> subCategories;

  @JsonIgnore
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "category")
  private Set<Product> products;

}

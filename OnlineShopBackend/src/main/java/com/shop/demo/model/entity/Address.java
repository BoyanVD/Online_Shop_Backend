package com.shop.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "address")
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "street")
  private String street;

  @JsonIgnore
  @EqualsAndHashCode.Exclude
  @ManyToOne
  @JoinColumn(name = "city_id")
  private City city;

  @JsonIgnore
  @EqualsAndHashCode.Exclude
  @ManyToOne
  @JoinColumn(name = "address_type_id")
  private AddressType addressType;

  @JsonIgnore
  @EqualsAndHashCode.Exclude

  @ManyToOne
  private User user;
}

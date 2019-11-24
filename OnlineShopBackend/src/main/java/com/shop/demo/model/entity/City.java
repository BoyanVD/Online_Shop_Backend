package com.shop.demo.model.entity;

import com.shop.demo.model.dto.AddNewAddressDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "city")
public class City {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "postal_code")
  private int postalCode;

  @JsonIgnore
  @EqualsAndHashCode.Exclude
  @ManyToOne
  @JoinColumn(name = "country_id")
  private Country country;

  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "city")
  private Set<Address> addresses;

  public City(Country country, AddNewAddressDTO address){
    this.country = country;
    this.name = address.getCityName();
  }
}

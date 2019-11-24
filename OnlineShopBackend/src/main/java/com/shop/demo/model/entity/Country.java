package com.shop.demo.model.entity;

import com.shop.demo.model.dto.AddNewAddressDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "country")
public class Country {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "country")
  private Set<City> cities;

  public Country(AddNewAddressDTO address){
    this.name = address.getCountryName();
  }
}

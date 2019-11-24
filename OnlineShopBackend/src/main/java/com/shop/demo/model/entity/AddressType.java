package com.shop.demo.model.entity;

import com.shop.demo.model.dto.AddNewAddressDTO;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "address_type")
public class AddressType {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name")
  private String name;

  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "addressType")
  private Set<Address> addresses;

  public AddressType(AddNewAddressDTO address){
    this.name = address.getAddressTypeName();
  }
}

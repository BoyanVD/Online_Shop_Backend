package com.shop.demo.repository;

import com.shop.demo.model.entity.AddressType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressTypeRepository extends JpaRepository<AddressType, Long> {

  public AddressType findAddressTypeByName(String name);
}

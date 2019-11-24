package com.shop.demo.repository;

import com.shop.demo.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

  public List<Address> findAddressesByUserId(Long id);
}

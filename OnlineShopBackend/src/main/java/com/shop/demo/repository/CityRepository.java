package com.shop.demo.repository;

import com.shop.demo.model.entity.Address;
import com.shop.demo.model.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {

  public City findCityByAddressesContains(Address address);

  public City findCityByName(String name);
}

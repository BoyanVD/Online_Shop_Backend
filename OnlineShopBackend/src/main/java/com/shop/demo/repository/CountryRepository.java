package com.shop.demo.repository;

import com.shop.demo.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {

  public Country findCountryByName(String name);
}

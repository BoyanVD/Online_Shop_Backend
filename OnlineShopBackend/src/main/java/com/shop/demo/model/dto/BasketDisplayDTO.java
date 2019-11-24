package com.shop.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BasketDisplayDTO {

  @Positive(message = "Basket id can't be negative or zero")
  private Long id;

  private Set<ProductInBasketDTO> products;
}

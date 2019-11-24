package com.shop.demo.model.dto;

import lombok.*;

import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCardDisplayDTO {

  @Positive(message = "Gift Card id can't be negative or zero")
  private Long id;

  @Positive(message = "credit card balance must be positive")
  private Double balance;
}

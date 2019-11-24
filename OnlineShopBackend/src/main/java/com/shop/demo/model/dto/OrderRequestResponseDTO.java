package com.shop.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestResponseDTO {

  @Positive(message = "Order id can't be negative or zero")
  private Long id;

  private LocalDate orderDate;
  private LocalDate deliveryDate;
}

package com.shop.demo.model.dto;

import com.shop.demo.model.entity.City;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDisplayDTO {

  @Positive(message = "Address id can't be negative or zero")
  private Long id;

  @NotBlank(message = "Street name can't be blank")
  private String Street;

  @NotBlank(message = "City name can't be blank")
  private String city;
}

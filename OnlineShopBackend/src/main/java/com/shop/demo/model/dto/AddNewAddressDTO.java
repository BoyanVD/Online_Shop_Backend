package com.shop.demo.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddNewAddressDTO {

  @NotBlank(message = "Street name can't be blank")
  private String streetName;

  @NotBlank(message = "Street name can't be blank")
  private String cityName;

  @Positive(message = "Postal code can't be negative number or zero")
  private int postalCode;

  @NotBlank(message = "Street name can't be blank")
  private String countryName;

  @NotBlank(message = "Address Type can't be blank")
  private String addressTypeName;
}

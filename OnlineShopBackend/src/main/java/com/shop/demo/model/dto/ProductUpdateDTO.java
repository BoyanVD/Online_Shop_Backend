package com.shop.demo.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateDTO{

	@NotNull(message = "Product id can't be null!")
	@PositiveOrZero(message = "Product id can't be negative number!")
	private Long id;

	@NotBlank(message = "Product name can't be blank")
	private String name;

	@NotBlank(message = "Product description can't be blank")
	private String description;

	@Positive(message = "Product price can't be negative or zero")
	private Double price;

	@Positive(message = "Product quantity can't be negative or zero")
	private Integer quantity;

	@Positive(message = "Product seller id can't be negative or zero")
	private Long sellerId;
}

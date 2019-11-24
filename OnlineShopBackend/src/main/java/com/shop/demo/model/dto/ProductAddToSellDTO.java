package com.shop.demo.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductAddToSellDTO {

	@NotBlank(message = "Product name can't be blank!")
	private String name;

	@NotBlank(message = "Product description can't be blank!")
	private String description;

	@NotNull(message = "Product price can't be null!")
	@PositiveOrZero(message = "Product price can't be negative number!")
	private Double price;

	@NotNull(message = "Product quantity can't be null!")
	@PositiveOrZero(message = "Product quantity can't be negative number!")
	private Integer quantity;

	@NotNull(message = "Product category id can't be null!")
	@PositiveOrZero(message = "Product category id can't be negative number!")
	private Long categoryId;

	@NotNull(message = "Product seller id can't be null!")
	@PositiveOrZero(message = "Product seller id can't be negative number!")
	private Long sellerId;
}

package com.shop.demo.model.dto;

import lombok.*;

import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductHelperDTO {

	@Positive(message = "Product id can't be negative or zero")
	private int id;
}

package com.shop.demo.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProductDTO {

	@NotNull(message = "Product id can't be null!")
	@PositiveOrZero(message = "Product id can't be negative number!")
	private Long productId;

	@NotNull(message = "Product seller id can't be null!")
	@PositiveOrZero(message = "Product seller id can't be negative number!")
	private Long sellerId;
}

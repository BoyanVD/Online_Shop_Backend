package com.shop.demo.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCardOrderDTO {

	@NotNull(message = "Credit card id can't be null!")
	@Positive(message = "Credit card id must be positive number!")
	private Long creditCardId;
}

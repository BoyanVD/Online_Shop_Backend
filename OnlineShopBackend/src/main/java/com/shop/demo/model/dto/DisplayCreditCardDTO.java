package com.shop.demo.model.dto;

import java.time.LocalDate;

import lombok.*;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisplayCreditCardDTO {

	@Positive(message = "Credit card id can't be negative or zero")
	private Long id;

	@FutureOrPresent(message = "Credit card has expired!")
	private LocalDate expiringDate;

	@NotBlank(message = "Cardholder name can't be blank")
	private String cardHolderName;

}

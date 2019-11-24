package com.shop.demo.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountRegistrationDTO {
	
	@NotBlank(message = "IBAN can't be blank!")
	private String iban;

	@NotNull
	@PositiveOrZero(message  = "Bank account's balance can't be negative!")
	private Double balance;
}

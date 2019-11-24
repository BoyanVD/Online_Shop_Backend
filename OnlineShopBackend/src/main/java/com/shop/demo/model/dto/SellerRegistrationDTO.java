package com.shop.demo.model.dto;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SellerRegistrationDTO {

	@NotBlank(message = "Seller's legal name can't be blank!")
	private String legalName;

	@NotBlank(message = "Seller's business dislpay name can't be blank!")
	private String businessDisplayName;
	
	@Valid
	@NotNull(message = "Seller's bank account can't be null!")
	private BankAccountRegistrationDTO account;
	
}

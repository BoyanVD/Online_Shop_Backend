package com.shop.demo.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDTO {

	private static final int PASSWORD_MINIMUM_CHARACTERS = 6;
	
	@NotBlank(message = "Invalid name!") 
	private String name;

	@Email(message = "Invalid email!")
	private String email;

	@NotBlank(message = "Invalid password!")
	@Size(min = PASSWORD_MINIMUM_CHARACTERS, message = "Password must be at least 6 characters!")
	private String password;

	@NotBlank(message = "Invalid password!")
	@Size(min = PASSWORD_MINIMUM_CHARACTERS, message = "Password must be at least 6 characters!")
	private String reEnteredPassword;
	
}

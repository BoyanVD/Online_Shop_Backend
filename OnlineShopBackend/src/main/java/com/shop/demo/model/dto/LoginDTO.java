package com.shop.demo.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

	private static final int PASSWORD_MINIMUM_CHARACTERS = 6;
	
	@Email(message = "Invalid email!")
	private String email;

	@NotBlank(message = "Invalid password!")
	@Size(min = PASSWORD_MINIMUM_CHARACTERS, message = "Password must be at least 6 characters!")
	private String password;
	
	
}

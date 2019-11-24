package com.shop.demo.model.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Positive;

@Data
public class CreateResponseDTO extends ResponseDTO{

	@Positive(message = "Id can't be negative or zero")
	private Long id;
	
	public CreateResponseDTO(Integer statusCode, String message, Long id) {
		super(statusCode, message);
		this.id = id;
	}
}

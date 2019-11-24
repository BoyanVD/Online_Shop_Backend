package com.shop.demo.model.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class ResponseDTO {
	
	private LocalDateTime time;
	private Integer statusCode;
	private String message;
	
	public ResponseDTO(Integer statusCode, String message) {
		this.time = LocalDateTime.now();
		this.statusCode = statusCode;
		this.message = message;
	}
	
	

}

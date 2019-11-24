package com.shop.demo.model.dto;

import com.shop.demo.model.entity.Product;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductListDTO {

	@Positive(message = "Product id can't be negative or zero")
	private Long id;

	@NotBlank(message = "Product name can't be blank")
	private String name;

	@Positive(message = "Product price can't be negative or zero")
	private Double price;


	public ProductListDTO(Product product){
		this.id = product.getId();
		this.name = product.getName();
		this.price = product.getPrice();
	}
}

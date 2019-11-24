package com.shop.demo.model.dto;


import com.shop.demo.model.entity.Product;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsDTO {

	@Positive(message = "Product id can't be negative or zero")
	private Long id;

	@NotBlank(message = "Product name can't be blank")
	private String name;

	@NotBlank(message = "Product description can't be blank")
	private String description;

	@Positive(message = "Product price can't be negative or zero")
	private Double price;

	@NotBlank(message = "Seller name can't be blank")
	private String sellerName;

	public ProductDetailsDTO(Product product){
		this.id = product.getId();
		this.name = product.getName();
		this.description = product.getDescription();
		this.price = product.getPrice();
		this.sellerName = product.getSeller().getLegalName();
	}
	
}

package com.shop.demo.model.dto;

import com.shop.demo.model.entity.BasketProduct;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInBasketDTO {

	@Positive(message = "Product id can't be negative or zero")
	private Long productId;

	@NotBlank(message = "Product name can't be blank")
	private String name;

	@Positive(message = "Product price can't be negative or zero")
	private Double price;

	@Positive(message = "Product quantity can't be negative or zero")
	private Integer quantity;

	@Positive(message = "Seller id can't be negative or zero")
	private Long sellerId;

	public ProductInBasketDTO(BasketProduct basketProduct){

		this.productId = basketProduct.getProduct().getId();
		this.name = basketProduct.getProduct().getName();
		this.price = basketProduct.getProduct().getPrice();
		this.quantity = basketProduct.getQuantity();
		this.sellerId = basketProduct.getProduct().getSeller().getId();
	}
}

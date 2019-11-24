package com.shop.demo.controller;
import java.util.List;


import com.shop.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.demo.model.dto.ProductDetailsDTO;
import com.shop.demo.model.dto.ProductListDTO;
import com.shop.demo.exception.NoSuchProductException;


@RestController
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public List<ProductListDTO> getProductsFromCategory(@RequestParam(name="CategoryId", required=false) Long categoryId,
																											@RequestParam(name="ProductName", required=false) String productName, @RequestParam(name="SortBy",
			required=false) String sortBy) throws NoSuchProductException {
		return this.productService.getFilteredSortedProducts(productName, categoryId, sortBy);
	}

	@GetMapping("/product/{id}")
	public ProductDetailsDTO getProduct(@PathVariable Long id) throws NoSuchProductException  {
		return productService.findProductById(id);
	}


}

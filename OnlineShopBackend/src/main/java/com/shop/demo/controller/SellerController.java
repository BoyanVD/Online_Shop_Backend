package com.shop.demo.controller;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.shop.demo.exception.*;
import com.shop.demo.model.entity.User;
import com.shop.demo.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.demo.model.dto.CreateResponseDTO;
import com.shop.demo.model.dto.DeleteProductDTO;
import com.shop.demo.model.dto.ProductAddToSellDTO;
import com.shop.demo.model.dto.ProductUpdateDTO;
import com.shop.demo.model.dto.ResponseDTO;
import com.shop.demo.util.Validation;

@RestController
public class SellerController {

	@Autowired
	private SellerService sellerService;
	
	@PostMapping("/addProductForSell")
	public ResponseEntity<CreateResponseDTO> addProductToSell(@RequestBody @Valid ProductAddToSellDTO product, HttpServletRequest request) throws NotLoggedInException, SellerException {
		Validation.validateLogIn(request);
		User user = (User) request.getSession().getAttribute("user");
		Long newProductId = sellerService.addProductForSell(user, product);
		return new ResponseEntity<CreateResponseDTO>(new CreateResponseDTO(HttpStatus.CREATED.value(), "New product successfully created!", newProductId), HttpStatus.CREATED);
	}
	@DeleteMapping("/deleteProductForSell")
	public ResponseEntity<ResponseDTO> deleteProductToSell(@RequestBody @Valid DeleteProductDTO product, HttpServletRequest request) throws SellerException, NotLoggedInException, NoSuchProductException {
		Validation.validateLogIn(request);
		User user = (User) request.getSession().getAttribute("user");
		sellerService.deleteProduct(user, product);
		return new ResponseEntity<ResponseDTO>(new ResponseDTO(HttpStatus.OK.value(), "Product deleted successfully!"), HttpStatus.OK);
	}
	@PatchMapping("/updateProductInfo")
	public ResponseEntity<ResponseDTO> updateProductToSell(@RequestBody @Valid ProductUpdateDTO product, HttpServletRequest request  ) throws NotLoggedInException, SellerException, NoSuchProductException, CategoryException {
		Validation.validateLogIn(request);
		User user = (User) request.getSession().getAttribute("user");
		sellerService.updateProduct(user, product);
		return new ResponseEntity<ResponseDTO>(new ResponseDTO(HttpStatus.OK.value(), "Product updated successfully!"), HttpStatus.OK);
	}
}

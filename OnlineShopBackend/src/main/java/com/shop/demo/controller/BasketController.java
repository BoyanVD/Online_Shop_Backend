package com.shop.demo.controller;


import javax.servlet.http.HttpServletRequest;

import com.shop.demo.exception.*;
import com.shop.demo.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shop.demo.model.dto.ResponseDTO;
import com.shop.demo.service.BasketService;
import com.shop.demo.util.Validation;

@RestController
public class BasketController {
	
	private static final String USER_SESSION_ATTRIBUTE = "user";

	@Autowired
	private BasketService basketService;

	@GetMapping("/add-to-basket")
	public ResponseEntity<ResponseDTO> addProductToBasket(@RequestParam(name="productId", required=true) Long productId, @RequestParam(name="quantity", required=false, defaultValue = "1") Integer quantity, HttpServletRequest request) throws NotLoggedInException, ProductException, BasketException, NotEnoughQuantityException, NoSuchProductException {
		Validation.validateLogIn(request);
		User user = (User) request.getSession().getAttribute(USER_SESSION_ATTRIBUTE);
		this.basketService.addProductToBasket(user, quantity, productId);
		return new ResponseEntity<ResponseDTO>(new ResponseDTO(HttpStatus.OK.value(), "Product added to basket successfully!"), HttpStatus.OK);
	}
	
	@DeleteMapping("/remove-from-basket")
	public ResponseEntity<ResponseDTO> removeProductFromBasket(@RequestParam(name="productId", required=true) Long productId, HttpServletRequest request) throws NoSuchProductException, NotLoggedInException, BasketException {
		Validation.validateLogIn(request);
		User user = (User) request.getSession().getAttribute(USER_SESSION_ATTRIBUTE);
		this.basketService.removeProductFromUserBasket(productId, user.getBasket());
		return new ResponseEntity<ResponseDTO>(new ResponseDTO(HttpStatus.OK.value(), "Product removed from basket successfully!"), HttpStatus.OK);
	}
	

}

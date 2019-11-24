package com.shop.demo.controller;

import com.shop.demo.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.shop.demo.model.dto.ResponseDTO;

@ControllerAdvice
public class ExceptionHandlingController {


	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDTO> generalExceptionHandler(Exception e){

		ResponseDTO response = new ResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An internal error has been encountered! Please wait until we fix it!");
		return new ResponseEntity<ResponseDTO>(response,HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler({MethodArgumentNotValidException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
	public ResponseEntity<ResponseDTO> validationExceptionHandler(Exception e){

		ResponseDTO response = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return new ResponseEntity<ResponseDTO>(response,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler({AddressException.class, CategoryException.class, ProductException.class,
		UserException.class, InvalidPasswordException.class, CreditCardException.class, GiftCardException.class, OrderException.class,
		BasketException.class, NotEnoughMoneyInCreditCardException.class, BankAccountException.class, SellerException.class,
		NotEnoughQuantityException.class, AdminException.class, AlredySuchUserCreditCardException.class})
	public ResponseEntity<ResponseDTO> addressExceptionHandler(Exception e){

		ResponseDTO response = new ResponseDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		return new ResponseEntity<ResponseDTO>(response,HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotLoggedInException.class)
	public ResponseEntity<ResponseDTO> unauthorizedExceptionHandler(Exception e){
		ResponseDTO response = new ResponseDTO(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
		return new ResponseEntity<ResponseDTO>(response,HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(NotAdminException.class)
	public ResponseEntity<ResponseDTO> forbiddenExceptionHandler(Exception e){

		ResponseDTO response = new ResponseDTO(HttpStatus.FORBIDDEN.value(), e.getMessage());
		return new ResponseEntity<ResponseDTO>(response,HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler({NoSuchEmailException.class, EmptyBasketException.class, NoSuchProductException.class, SellerDoesNotExistException.class})
	public ResponseEntity<ResponseDTO> notFoundExceptionHandler(Exception e){
		ResponseDTO response = new ResponseDTO(HttpStatus.NOT_FOUND.value(), e.getMessage());
		return new ResponseEntity<ResponseDTO>(response,HttpStatus.NOT_FOUND);
	}
}

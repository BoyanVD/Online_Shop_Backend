package com.shop.demo.controller;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.shop.demo.exception.*;
import com.shop.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.demo.model.dto.AddCategoryDTO;
import com.shop.demo.model.dto.CreateResponseDTO;
import com.shop.demo.model.dto.DeleteProductDTO;
import com.shop.demo.model.dto.ResponseDTO;
import com.shop.demo.util.Validation;


@RestController
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	
	@PostMapping("/addCategory")
	public ResponseEntity<CreateResponseDTO> addCategory(@RequestBody @Valid AddCategoryDTO category, HttpServletRequest request) throws  NotLoggedInException, NotAdminException, CategoryException {
		Validation.validateAdminUser(request);
		Long categoryId = adminService.addCategory(category);
		return new ResponseEntity<CreateResponseDTO>(new CreateResponseDTO(HttpStatus.CREATED.value(), "Category created successfully!", categoryId), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/deleteProduct")
	public ResponseEntity<ResponseDTO> deleteProduct(@RequestBody DeleteProductDTO productId, HttpServletRequest request) throws NotLoggedInException, NotAdminException, NoSuchProductException {
		Validation.validateAdminUser(request);
		adminService.deleteProduct(productId.getProductId());
		return new ResponseEntity<ResponseDTO>(new ResponseDTO(HttpStatus.OK.value(), "Product deleted successfully!"), HttpStatus.OK);
	}

}

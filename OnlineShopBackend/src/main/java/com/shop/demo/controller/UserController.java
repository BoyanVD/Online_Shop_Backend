package com.shop.demo.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.shop.demo.exception.*;
import com.shop.demo.model.dto.*;
import com.shop.demo.model.entity.User;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.shop.demo.service.UserService;
import com.shop.demo.util.Validation;

@RestController
public class UserController {

	
	private static final String USER_SESSION_ATTRIBUTE = "user";
	private static final int SESSION_DURATION = 30;
	@Autowired
	private UserService userService;


	@PostMapping("/register")
	public ResponseEntity<CreateResponseDTO> register(@RequestBody @Valid UserRegistrationDTO user) throws UserException {
		Validation.validateUserPassword(user.getPassword(), user.getReEnteredPassword());
		Long newUserId = userService.register(user);
		return new ResponseEntity<CreateResponseDTO>(new CreateResponseDTO(HttpStatus.CREATED.value(), "User registered successfully!", newUserId), HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<ResponseDTO> getUser(@RequestBody @Valid LoginDTO user , HttpServletRequest request) throws SQLException, UserException, NoSuchEmailException, InvalidPasswordException, CreditCardException, AddressException, GiftCardException, OrderException, BasketException {
		com.shop.demo.model.entity.User u = userService.login(user);
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(SESSION_DURATION);
		session.setAttribute(USER_SESSION_ATTRIBUTE, u);
		return new ResponseEntity<ResponseDTO>(new ResponseDTO(HttpStatus.OK.value(), "Log in successful!"), HttpStatus.OK);
	}
	@GetMapping("/profile")
	public UserProfileDTO getUserProfile(HttpServletRequest request) throws NotLoggedInException {
		Validation.validateLogIn(request);
		User user = (User) request.getSession().getAttribute(USER_SESSION_ATTRIBUTE);
		UserProfileDTO u = new UserProfileDTO(user.getName(), user.getEmail());
		return u;
	}
	@GetMapping("/orders")
	public Set<com.shop.demo.model.entity.Order> getUserOrders(HttpServletRequest request) throws NotLoggedInException {
		Validation.validateLogIn(request);
		User user = ((User)request.getSession().getAttribute(USER_SESSION_ATTRIBUTE));
		return userService.findUserOrders(user);
	}
	@GetMapping("/addresses")
	public List<AddressDisplayDTO> getUserAddresses(HttpServletRequest request) throws NotLoggedInException {
		Validation.validateLogIn(request);
		return userService.findUserAddresses((User)request.getSession().getAttribute(USER_SESSION_ATTRIBUTE));
	}
	@GetMapping("/creditCards")
	public Set<DisplayCreditCardDTO> getUserCreditCards(HttpServletRequest request) throws NotLoggedInException {
		Validation.validateLogIn(request);
		User user  =	((User)request.getSession().getAttribute(USER_SESSION_ATTRIBUTE));
		Set<DisplayCreditCardDTO> creditCards = userService.findUserCreditCards(user);
		return creditCards;
	}
	@GetMapping("/giftCards")
	public Set<GiftCardDisplayDTO> getUserGiftCards(HttpServletRequest request) throws NotLoggedInException {
		Validation.validateLogIn(request);
		Hibernate.initialize(request.getSession().getAttribute(USER_SESSION_ATTRIBUTE));
		Set<GiftCardDisplayDTO> giftCards = ((User)request.getSession().getAttribute(USER_SESSION_ATTRIBUTE))
				.getGiftCards().stream().map(giftCard -> new GiftCardDisplayDTO(giftCard.getId(), giftCard.getBalance())).collect(Collectors.toSet());
		return giftCards;
	}
	@GetMapping("/basket")
	public BasketDisplayDTO getUserBasket(HttpServletRequest request) throws NotLoggedInException {
		Validation.validateLogIn(request);
		User user = ((User)request.getSession().getAttribute(USER_SESSION_ATTRIBUTE));
		BasketDisplayDTO basket = userService.getUserBasketWithProducts(user);
		return basket;
	}

	@GetMapping("/signout")
	public ResponseEntity<ResponseDTO> logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return new ResponseEntity<ResponseDTO>(new ResponseDTO(HttpStatus.OK.value(), "Sign out successful!"), HttpStatus.OK);
	}
	
	@PostMapping("/makeOrder")
	public OrderRequestResponseDTO makeOrder(@RequestBody @Valid CreditCardOrderDTO creditCard ,HttpServletRequest request) throws EmptyBasketException, NotLoggedInException, CreditCardException, NotEnoughMoneyInCreditCardException, ProductException, NoSuchProductException, NotEnoughQuantityException, OrderException, UserException{
		Validation.validateLogIn(request);
		Hibernate.initialize(request.getSession().getAttribute(USER_SESSION_ATTRIBUTE));
		return userService.makeOrder((User) request.getSession().getAttribute(USER_SESSION_ATTRIBUTE), creditCard);
	}
	
	@PostMapping("/add-new-address")
	public ResponseEntity<CreateResponseDTO> addNewAddress(@RequestBody @Valid AddNewAddressDTO address, HttpServletRequest request) throws NotLoggedInException, AddressException, UserException {
		Validation.validateLogIn(request);
		Long newAddressId = this.userService.addNewAddress((User) request.getSession().getAttribute(USER_SESSION_ATTRIBUTE), address);
		return new ResponseEntity<CreateResponseDTO>(new CreateResponseDTO(HttpStatus.CREATED.value(), "Address created successfully!", newAddressId), HttpStatus.CREATED);
	}
	
	@PostMapping("/registerSeller")
	public ResponseEntity<CreateResponseDTO> registerNewSeller(@RequestBody @Valid SellerRegistrationDTO seller, HttpServletRequest request) throws NotLoggedInException, BankAccountException, SellerException {
		Validation.validateLogIn(request);
		Long newSellerId = userService.registerNewSeller((User) request.getSession().getAttribute(USER_SESSION_ATTRIBUTE), seller);
		return new ResponseEntity<CreateResponseDTO>(new CreateResponseDTO(HttpStatus.CREATED.value(), "Seller created successfully!", newSellerId), HttpStatus.CREATED);
	}
	
	@PostMapping("/addcreditCard")
	public ResponseEntity<CreateResponseDTO> addNewCreditcard(@RequestBody @Valid AddCreditCardDTO creditCard, HttpServletRequest request) throws NotLoggedInException, CreditCardException, UserException, AlredySuchUserCreditCardException {
		Validation.validateLogIn(request);
		Long newCreditCardId = userService.addNewCreditCard((User) request.getSession().getAttribute(USER_SESSION_ATTRIBUTE), creditCard);
		return new ResponseEntity<CreateResponseDTO>(new CreateResponseDTO(HttpStatus.CREATED.value(), "Credit card created successfully!", newCreditCardId), HttpStatus.CREATED);
	}
}

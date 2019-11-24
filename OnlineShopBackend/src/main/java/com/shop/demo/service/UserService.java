package com.shop.demo.service;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.shop.demo.exception.*;
import com.shop.demo.model.dto.*;
import com.shop.demo.model.entity.*;
import com.shop.demo.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class UserService {

	private static final int DELIVERY_TIME_CONSTANT = 30;

	private final UserRepository userRepository;
	private final GiftCardRepository giftCardRepository;
	private final OrderRepository orderRepository;
	private final SellerRepository sellerRepository;
	private final BasketRepository basketRepository;
	private final AddressRepository addressRepository;
	private final CityRepository cityRepository;
	private final CreditCardRepository creditCardRepository;
	private final BasketProductRepository basketProductRepository;
	private final BankAccountRepository bankAccountRepository;
	private final ProductRepository productRepository;
	private final CountryRepository countryRepository;
	private final AddressTypeRepository addressTypeRepository;

	@Autowired
	public UserService(UserRepository userRepository,
										 CountryRepository countryRepository,
										 GiftCardRepository giftCardRepository,
										 OrderRepository orderRepository,
										 AddressTypeRepository addressTypeRepository,
										 SellerRepository sellerRepository,
										 BasketRepository basketRepository,
										 AddressRepository addressRepository,
										 CityRepository cityRepository,
										 CreditCardRepository creditCardRepository,
										 BasketProductRepository basketProductRepository,
										 BankAccountRepository bankAccountRepository,
										 ProductRepository productRepository) {
		this.userRepository = userRepository;
		this.countryRepository = countryRepository;
		this.giftCardRepository = giftCardRepository;
		this.orderRepository = orderRepository;
		this.addressTypeRepository = addressTypeRepository;
		this.sellerRepository = sellerRepository;
		this.basketRepository = basketRepository;
		this.addressRepository = addressRepository;
		this.cityRepository = cityRepository;
		this.creditCardRepository = creditCardRepository;
		this.basketProductRepository = basketProductRepository;
		this.bankAccountRepository = bankAccountRepository;
		this.productRepository = productRepository;
	}

	/**
	 * The method finds user by LoginDTO given and sets all relational data of the User
	 * @param user
	 * @returns User u
	 * @throws UserException
	 * @throws InvalidPasswordException
	 * @throws NoSuchEmailException
	 */
	public User login(LoginDTO user) throws InvalidPasswordException, NoSuchEmailException, UserException {

		User u = userRepository.findUserByEmail(user.getEmail());
		if(u == null){
			throw new NoSuchEmailException("Invalid email !!!");
		}
		u.setGiftCards(giftCardRepository.findGiftCardsByUserId(u.getId()));
		u.setOrders(orderRepository.findOrdersByUserId(u.getId()));
		u.setSellers(sellerRepository.findSellersByUserId(u.getId()));
		u.setBasket(basketRepository.findBasketByUserId(u.getId()));

		if(!this.stringToSha1(user.getPassword()).equals(u.getPassword())){
			throw new InvalidPasswordException("Invalid password !!!");
		}

		return u;
	}

	public boolean isBasketEmpty(Basket basket){
		Basket b = basketRepository.findBasketByUserId(basket.getUser().getId());

		return (b.getBasketProducts().isEmpty());
	}
	/**
	 * The method finds credit card by id, makes transaction for every product of user basket and refreshes user basket
	 * @param u
	 * @param c
	 * @returns OrderRequestResponseDTO order
	 * @throws CreditCardException
	 * @throws NotEnoughMoneyInCreditCardException
	 * @throws ProductException
	 * @throws NoSuchProductException
	 * @throws NotEnoughQuantityException
	 * @throws OrderException
	 */
	@Transactional
	public OrderRequestResponseDTO makeOrder(User u, CreditCardOrderDTO c) throws CreditCardException, NotEnoughMoneyInCreditCardException,
			NoSuchProductException, NotEnoughQuantityException, EmptyBasketException {

		if(!this.isThereUserAndCreditCardCombinationAlready(u.getId(), c.getCreditCardId())) {
			throw new CreditCardException("This credit card does not match the user!");
		}
		if(this.isBasketEmpty(u.getBasket())){
			throw new EmptyBasketException("Empty basket to make order of !!!");
		}
		CreditCard card = creditCardRepository.findCreditCardById(c.getCreditCardId());
		this.makeTransaction(u, card);
		OrderRequestResponseDTO order = this.createOrder(u.getBasket());
		this.refreshUserBasket(u);
		return order;
	}

	/**
	 * The method refreshes the basket of given User after order
	 * @param u
	 */
	public void refreshUserBasket(User u) {

		Basket basket = new Basket(null, u, new HashSet<BasketProduct>());
		basketRepository.delete(u.getBasket());
		basketRepository.save(basket);
		u.setBasket(basket);
		userRepository.save(u);
	}

	/**
	 * The method makes multiple transactions for every product in user basket and reduces quantity.
	 * @param u
	 * @param card
	 * @throws NotEnoughMoneyInCreditCardException
	 * @throws ProductException
	 * @throws NoSuchProductException
	 * @throws NotEnoughQuantityException
	 */
	@Transactional
	public void makeTransaction(User u, CreditCard card) throws NotEnoughMoneyInCreditCardException, NoSuchProductException, NotEnoughQuantityException {

		if(!this.isThereEnoughMoneyForOrder(card, u)) {
			throw new NotEnoughMoneyInCreditCardException("Not enough money for order");
		}
		Set<ProductInBasketDTO> productsInUserBasket = this.getUserBasketWithProducts(u).getProducts();
		List<Product> productsFromDB = productRepository.findProductsByIdIn(productsInUserBasket.stream().map(p -> p.getProductId()).collect(Collectors.toList()));
		if(productsInUserBasket.size() != productsFromDB.size()){
			throw new NoSuchProductException("Invalid products to buy");
		}

		for(ProductInBasketDTO product : productsInUserBasket) {

			if(product.getQuantity() > productsFromDB.stream().filter(p -> p.getName().equals(product.getName())).findFirst().get().getQuantity()){
				throw new NotEnoughQuantityException("Not enough quantity from product  !!!");
			}

			card.setBalance(card.getBalance() - (product.getPrice() * product.getQuantity()));
			creditCardRepository.save(card);

			Seller seller = sellerRepository.findSellerById(product.getSellerId());
			BankAccount bankAccount = seller.getBankAccounts().stream().findFirst().get();
			bankAccount.setBalance(bankAccount.getBalance() + (product.getPrice() * product.getQuantity()));
			bankAccountRepository.save(bankAccount);

			Product p = productRepository.findProductById(product.getProductId());
			p.setQuantity(p.getQuantity() - product.getQuantity());
			productRepository.save(p);

		}
	}

	public boolean isThereEnoughMoneyForOrder(CreditCard card, User u){
		BasketDisplayDTO products = this.getUserBasketWithProducts(u);
		double orderPrice = 0;
		for(ProductInBasketDTO product : products.getProducts()){
			orderPrice += product.getPrice()* product.getQuantity();
		}

		return !(orderPrice > card.getBalance());
	}

	/**
	 * This method checks if business display name is available
	 * @param businessDisplayName
	 * @return boolean isAvailable
	 */
	public boolean isBusinessDisplayNameAvailable(String businessDisplayName) {

		Seller seller = sellerRepository.findSellerByBusinessDisplayName(businessDisplayName);

		return (seller == null);
	}

	/**
	 * The method makes new Seller registration to User profile given
	 * @param user
	 * @param seller
	 * @throws BankAccountException
	 * @throws SellerException
	 */
	public Long registerNewSeller(User user, SellerRegistrationDTO seller) throws BankAccountException, SellerException {
		
		if(this.isThereSuchBankAccountAlready(seller.getAccount())) {
			throw new BankAccountException("Already such bank account");
		}
		if(!this.isBusinessDisplayNameAvailable(seller.getBusinessDisplayName())) {
			throw new SellerException("Business display name already taken");
		}

		BankAccount bankAccount = new BankAccount(seller.getAccount());

		Seller newSeller = new Seller(seller, user);

		HashSet<BankAccount> newSellerBankAccounts = new HashSet<>();
		newSellerBankAccounts.add(bankAccount);
		bankAccount.setSeller(newSeller);
		newSeller.setBankAccounts(newSellerBankAccounts);

		Long sellerId = sellerRepository.save(newSeller).getId();
		bankAccountRepository.save(bankAccount);

		return sellerId;
	}

	/**
	 * This method checks if there is such bank account in DB
	 * @param bankAccount
	 * @return boolean isThereSuchBankAccount
	 */
	public boolean isThereSuchBankAccountAlready(BankAccountRegistrationDTO bankAccount) {
		BankAccount account = bankAccountRepository.findBankAccountByIban(bankAccount.getIban());

		return (account != null);
	}
	
	/**
	 * The method checks in DB if there is such user/credit card combination already
	 * @param userId
	 * @param creditCardId
	 * @return boolean isTheresuchUserAndcreditCardCombination
	 */
	public boolean isThereUserAndCreditCardCombinationAlready(Long userId, Long creditCardId) {

		Set<CreditCard> userCreditCards = creditCardRepository.findCreditCardsByUsersId(userId);

		return (userCreditCards.contains(creditCardRepository.findCreditCardById(creditCardId)));
	}
	
	/**
	 * Method adds new credit card to a given user's list.
	 * @param user
	 * @param creditCard
	 * @return id of added credit card
	 */
	public Long addNewCreditCard(User user, AddCreditCardDTO creditCard) throws AlredySuchUserCreditCardException {

		CreditCard card = creditCardRepository.findCreditCardByCreditCardNumber(creditCard.getCreditCardNumber());
		if(card != null){
			Set<CreditCard> userCreditCards = creditCardRepository.findCreditCardsByUsersId(user.getId());
			if(userCreditCards.contains(card)){
				throw new AlredySuchUserCreditCardException("User already has this credit card !!!");
			}
			userCreditCards.add(card);
			user.setCreditCards(userCreditCards);
			userRepository.save(user);
			return card.getId();
		}

		card = new CreditCard(creditCard);
		Set<User> creditCardUsers = new HashSet<>();
		creditCardUsers.add(user);
		card.setUsers(creditCardUsers);

		Set<CreditCard> userCreditCards = creditCardRepository.findCreditCardsByUsersId(user.getId());
		userCreditCards.add(card);
		user.setCreditCards(userCreditCards);

		Long newCreditCardId = creditCardRepository.save(card).getId();
		userRepository.save(user);

		return newCreditCardId;
	}

	public Set<Order> findUserOrders(User user){
		return orderRepository.findOrdersByUserId(user.getId());
	}

	public List<AddressDisplayDTO> findUserAddresses(User user) {
		List<AddressDisplayDTO> addresses =
				addressRepository.findAddressesByUserId(user.getId()).stream()
						.map(address -> new AddressDisplayDTO(address.getId(), address.getStreet(),cityRepository.findCityByAddressesContains(address)
								.getName())).collect(Collectors.toList());
		return addresses;
	}

	public Set<DisplayCreditCardDTO> findUserCreditCards(User user) {
		Set<DisplayCreditCardDTO> creditCards = new HashSet<>();
		Set<CreditCard> userCreditCards = creditCardRepository.findCreditCardsByUsersId(user.getId());
		for(CreditCard creditCard : userCreditCards){
			creditCards.add(new DisplayCreditCardDTO(creditCard.getId(), creditCard.getExpiringDate(), creditCard.getCardHolderName()));
		}
		return creditCards;
	}

	public BasketDisplayDTO getUserBasketWithProducts(User user) {
		List<BasketProduct> basketproducts = basketProductRepository.findBasketProductByBasketUserId(user.getId());
		Set<ProductInBasketDTO> products = basketproducts.stream()
				.map(bp -> new ProductInBasketDTO(bp))
				.collect(Collectors.toSet());
		BasketDisplayDTO basket = new BasketDisplayDTO(user.getBasket().getId(), products);
		return basket;
	}

	/**
	 * This method creates order out of basket given
	 * @param basket
	 * @return OrderRequestResponseDTO order
	 */
	public OrderRequestResponseDTO createOrder(Basket basket) {

		Order order = new Order(null, LocalDate.now(), LocalDate.now().plusDays(DELIVERY_TIME_CONSTANT), basket.getUser());
		Long id = orderRepository.save(order).getId();
		return new OrderRequestResponseDTO(id, LocalDate.now(), LocalDate.now().plusDays(DELIVERY_TIME_CONSTANT));
	}

	/**
	 * This method checks if there is already such email address
	 * @param email
	 * @return boolean isThereSuchEmail
	 */
	public boolean isThereAlreadySuchEmail(String email) {
		User user = userRepository.findUserByEmail(email);

		return (user != null);
	}


	/**
	 * This method registers new User
	 * @param u
	 * @return Long registeredUserId
	 * @throws UserException
	 * @return newUserId
	 */
	@Transactional
	public Long register(UserRegistrationDTO u) throws UserException {
		if(this.isThereAlreadySuchEmail(u.getEmail())){
			throw new UserException("Already such email !!!");
		}

		User user = new User(u);
		userRepository.save(user);

		Long newUserId = userRepository.save(user).getId();

		Basket basket = new Basket(user);
		basketRepository.save(basket);

		return newUserId;
	}

	/**
	 * This converts String to sha1()
	 * @param string
	 * @return Long registeredUserId
	 * @throws UserException
	 */
	public static String stringToSha1(String string) throws UserException  {
		String sha1 = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.reset();
			digest.update(string.getBytes("utf8"));
			sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e){
			throw new UserException("A problem encountered while checking user password!", e);
		}
		return sha1;
	}

	/**
	 * This method adds new User Address by given user and address
	 * @param user
	 * @param address
	 * @return newAddressId
	 */
	@Transactional
	public Long addNewAddress(User user, AddNewAddressDTO address) {

		Country country = countryRepository.findCountryByName(address.getCountryName());
		if(country == null) {
			country = new Country(address);
			countryRepository.save(country);
		}

		City city = cityRepository.findCityByName(address.getCityName());
		if(city == null) {
			city = new City(country, address);
			cityRepository.save(city);
		}

		AddressType addressType = addressTypeRepository.findAddressTypeByName(address.getAddressTypeName());
		if(addressType == null) {
			addressType = new AddressType(address);
			addressTypeRepository.save(addressType);
		}

		Address newAddress = new Address(null, address.getStreetName(), city, addressType, user);

		Long addressId = addressRepository.save(newAddress).getId();

		return addressId;
	}
}

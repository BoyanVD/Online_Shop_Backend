package com.shop.demo.service;

import com.shop.demo.exception.*;
import com.shop.demo.model.dto.*;
import com.shop.demo.model.entity.*;
import com.shop.demo.repository.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;


import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.util.AssertionErrors.fail;

public class UserServiceTest {

  public static final String INVALID_EMAIL = "martin@abv.bg";
  public static final String VALID_PASSWORD = "123456";
  public static final String VALID_EMAIL = "ceco@abv.bg";
  public static final String INVALID_PASSWORD = "1234567";
  public static final int RANDOM_ID = 1;
  public static final double RANDOM_BALANCE = 123.5;
  public static final double RANDOM_PRICE = 456.7;
  public static final double BALANCE_HIGHER_THAN_PRODUCT_PRICE = 456.7;
  public static final double VALID_PRODUCT_PRICE = 23.6;
  public static final double CREDIT_CARD_BALANCE = 456.7;
  public static final int QUANTITY = 1;
  public static final int HIGHER_QUANTITY = 2;
  @Mock
  private UserRepository userRepository;
  @Mock
  private GiftCardRepository giftCardRepository;
  @Mock
  private OrderRepository orderRepository;
  @Mock
  private SellerRepository sellerRepository;
  @Mock
  private BasketRepository basketRepository;
  @Mock
  private AddressRepository addressRepository;
  @Mock
  private CityRepository cityRepository;
  @Mock
  private CreditCardRepository creditCardRepository;
  @Mock
  private BasketProductRepository basketProductRepository;
  @Mock
  private BankAccountRepository bankAccountRepository;
  @Mock
  private ProductRepository productRepository;
  @Mock
  private CountryRepository countryRepository;
  @Mock
  private AddressTypeRepository addressTypeRepository;
  @InjectMocks
  private UserService userServiceUnderTest;

  private User user;

  @Before
  public void setUp(){
    initMocks(this);
    this.user = new User();
    user.setId(Long.valueOf(RANDOM_ID));
  }

  @Test(expected = NoSuchEmailException.class)
  public void login_InvalidEmail() throws NoSuchEmailException, InvalidPasswordException, UserException {

    //given
    LoginDTO loginDto = new LoginDTO(INVALID_EMAIL, VALID_PASSWORD);

    //when
    when(userRepository.findUserByEmail(INVALID_EMAIL)).thenReturn(null);
    userServiceUnderTest.login(loginDto);

  }

  @Test(expected = InvalidPasswordException.class)
  public void login_InvalidPassword() throws NoSuchEmailException, InvalidPasswordException, UserException {

    //given
    LoginDTO loginDto = new LoginDTO(VALID_EMAIL, INVALID_PASSWORD);

    //when
    when(userRepository.findUserByEmail(loginDto.getEmail())).thenReturn(user);
    userServiceUnderTest.login(loginDto);
  }

  @Test
  public void login_ValidData() throws NoSuchEmailException, InvalidPasswordException, UserException {

    //given
    LoginDTO loginDto = new LoginDTO(VALID_EMAIL, VALID_PASSWORD);
    user.setPassword(UserService.stringToSha1(VALID_PASSWORD));

    //when
    when(userRepository.findUserByEmail(loginDto.getEmail())).thenReturn(user);
    userServiceUnderTest.login(loginDto);

  }

  @Test(expected = CreditCardException.class)
  public void makeOrder_CreditCardDoesntMatchUser() throws NotEnoughQuantityException, CreditCardException,
      NoSuchProductException, EmptyBasketException, NotEnoughMoneyInCreditCardException {

    user.setCreditCards(new HashSet<>());
    CreditCardOrderDTO card = new CreditCardOrderDTO(Long.valueOf(RANDOM_ID));

    userServiceUnderTest.makeOrder(user, card);
  }

  @Test(expected = EmptyBasketException.class)
  public void makeOrder_EmptyBasket() throws NotEnoughQuantityException, CreditCardException,
      NoSuchProductException, EmptyBasketException, NotEnoughMoneyInCreditCardException{

    CreditCardOrderDTO card = new CreditCardOrderDTO(Long.valueOf(RANDOM_ID));
    CreditCard creditCard = new CreditCard();
    Basket basket = new Basket(Long.valueOf(RANDOM_ID), user, new HashSet<>());
    Set<CreditCard> userCreditCards = new HashSet<>();

    when(creditCardRepository.findCreditCardById(card.getCreditCardId())).thenReturn(creditCard);
    when(creditCardRepository.findCreditCardsByUsersId(user.getId())).thenReturn(userCreditCards);
    when(basketRepository.findBasketByUserId(user.getId())).thenReturn(basket);
    userCreditCards.add(creditCard);
    user.setCreditCards(userCreditCards);
    user.setBasket(basket);

    userServiceUnderTest.makeOrder(user, card);
  }

  @Test(expected = NotEnoughMoneyInCreditCardException.class)
  public void makeOrder_NotEnoughMoneyForOrder() throws NotEnoughQuantityException, CreditCardException,
      NoSuchProductException, EmptyBasketException, NotEnoughMoneyInCreditCardException {

    Set<CreditCard> userCreditCards = new HashSet<>();
    CreditCardOrderDTO card = new CreditCardOrderDTO(Long.valueOf(RANDOM_ID));
    CreditCard creditCard = new CreditCard();
    creditCard.setBalance(RANDOM_BALANCE);
    Basket basket = new Basket();
    Set<BasketProduct> basketProducts = new HashSet<>();
    Product product = new Product();
    product.setPrice(RANDOM_PRICE);
    Seller seller = new Seller();
    seller.setId(Long.valueOf(RANDOM_ID));
    product.setSeller(seller);
    BasketProduct productInBasket = new BasketProduct();
    productInBasket.setProduct(product);
    productInBasket.setQuantity(RANDOM_ID);
    basketProducts.add(productInBasket);
    basket.setBasketProducts(basketProducts);
    basket.getBasketProducts().add(productInBasket);
    userCreditCards.add(creditCard);
    user.setBasket(basket);
    basket.setUser(user);
    Order order = new Order();
    order.setId(Long.valueOf(RANDOM_ID));

    when(creditCardRepository.findCreditCardById(card.getCreditCardId())).thenReturn(creditCard);
    when(creditCardRepository.findCreditCardsByUsersId(user.getId())).thenReturn(userCreditCards);
    when(basketRepository.findBasketByUserId(user.getId())).thenReturn(basket);
    when(orderRepository.save(any())).thenReturn(order);
    List<BasketProduct> bp = new LinkedList<>();
    bp.addAll(basketProducts);
    when(basketProductRepository.findBasketProductByBasketUserId(user.getId())).thenReturn(bp);

    userServiceUnderTest.makeOrder(user, card);
  }

  @Test(expected = NoSuchProductException.class)
  public void makeOrder_InvalidProductsToBuy() throws NotEnoughQuantityException, CreditCardException,
      NoSuchProductException, EmptyBasketException, NotEnoughMoneyInCreditCardException {

    Set<CreditCard> userCreditCards = new HashSet<>();
    CreditCardOrderDTO card = new CreditCardOrderDTO(Long.valueOf(RANDOM_ID));
    CreditCard creditCard = new CreditCard();
    creditCard.setBalance(BALANCE_HIGHER_THAN_PRODUCT_PRICE);
    Basket basket = new Basket();
    Set<BasketProduct> basketProducts = new HashSet<>();
    Product product = new Product();
    product.setPrice(VALID_PRODUCT_PRICE);
    Seller seller = new Seller();
    seller.setId(Long.valueOf(RANDOM_ID));
    product.setSeller(seller);
    BasketProduct productInBasket = new BasketProduct();
    productInBasket.setProduct(product);
    productInBasket.setQuantity(RANDOM_ID);
    basketProducts.add(productInBasket);
    basket.setBasketProducts(basketProducts);
    basket.getBasketProducts().add(productInBasket);
    userCreditCards.add(creditCard);
    user.setBasket(basket);
    basket.setUser(user);
    Order order = new Order();
    order.setId(Long.valueOf(RANDOM_ID));
    List<Product> productsFromDB = new LinkedList<>();
    productsFromDB.add(product);
    productsFromDB.add(new Product());

    when(creditCardRepository.findCreditCardById(card.getCreditCardId())).thenReturn(creditCard);
    when(creditCardRepository.findCreditCardsByUsersId(user.getId())).thenReturn(userCreditCards);
    when(basketRepository.findBasketByUserId(user.getId())).thenReturn(basket);
    when(orderRepository.save(any())).thenReturn(order);
    when(productRepository.findProductsByIdIn(any())).thenReturn(productsFromDB);
    List<BasketProduct> bp = new LinkedList<>();
    bp.addAll(basketProducts);
    when(basketProductRepository.findBasketProductByBasketUserId(user.getId())).thenReturn(bp);

    userServiceUnderTest.makeOrder(user, card);
  }

  @Test(expected = NotEnoughQuantityException.class)
  public void makeOrder_NotEnoughQuantityFromProduct() throws NotEnoughQuantityException, CreditCardException,
      NoSuchProductException, EmptyBasketException, NotEnoughMoneyInCreditCardException {

    Set<CreditCard> userCreditCards = new HashSet<>();
    CreditCardOrderDTO card = new CreditCardOrderDTO(Long.valueOf(RANDOM_ID));
    CreditCard creditCard = new CreditCard();
    creditCard.setBalance(CREDIT_CARD_BALANCE);
    userCreditCards.add(creditCard);

    Product product = new Product();
    product.setPrice(VALID_PRODUCT_PRICE);
    product.setQuantity(QUANTITY);
    product.setName("Random name");
    Seller seller = new Seller();
    seller.setId(Long.valueOf(RANDOM_ID));
    product.setSeller(seller);

    List<Product> productsFromDB = new LinkedList<>();
    productsFromDB.add(product);

    Basket basket = new Basket();
    Set<BasketProduct> basketProducts = new HashSet<>();

    BasketProduct productInBasket = new BasketProduct();
    productInBasket.setProduct(product);
    productInBasket.setQuantity(HIGHER_QUANTITY);
    basketProducts.add(productInBasket);
    basket.setBasketProducts(basketProducts);

    user.setBasket(basket);
    basket.setUser(user);

    when(creditCardRepository.findCreditCardById(card.getCreditCardId())).thenReturn(creditCard);
    when(creditCardRepository.findCreditCardsByUsersId(user.getId())).thenReturn(userCreditCards);
    when(basketRepository.findBasketByUserId(user.getId())).thenReturn(basket);
    when(productRepository.findProductsByIdIn(any())).thenReturn(productsFromDB);
    List<BasketProduct> userBasketProducts = new LinkedList<>();
    userBasketProducts.addAll(basket.getBasketProducts());
    when(basketProductRepository.findBasketProductByBasketUserId(user.getId())).thenReturn(userBasketProducts);

    userServiceUnderTest.makeOrder(user, card);
  }

  @Test
  public void makeOrder_ValidData() throws NotEnoughQuantityException, CreditCardException, NoSuchProductException, EmptyBasketException, NotEnoughMoneyInCreditCardException {

    Set<CreditCard> userCreditCards = new HashSet<>();
    CreditCardOrderDTO card = new CreditCardOrderDTO(Long.valueOf(RANDOM_ID));
    CreditCard creditCard = new CreditCard();
    creditCard.setBalance(CREDIT_CARD_BALANCE);
    userCreditCards.add(creditCard);

    Product product = new Product();
    product.setPrice(VALID_PRODUCT_PRICE);
    product.setQuantity(HIGHER_QUANTITY);
    product.setName("Random name");
    Seller seller = new Seller();
    seller.setId(Long.valueOf(RANDOM_ID));
    product.setSeller(seller);

    List<Product> productsFromDB = new LinkedList<>();
    productsFromDB.add(product);

    Basket basket = new Basket();
    Set<BasketProduct> basketProducts = new HashSet<>();

    BasketProduct productInBasket = new BasketProduct();
    productInBasket.setProduct(product);
    productInBasket.setQuantity(QUANTITY);
    basketProducts.add(productInBasket);
    basket.setBasketProducts(basketProducts);

    user.setBasket(basket);
    basket.setUser(user);

    BankAccount bankAccount = new BankAccount();
    bankAccount.setBalance(100);
    bankAccount.setSeller(seller);
    Set<BankAccount> sellerBankAccounts = new HashSet<>();
    sellerBankAccounts.add(bankAccount);
    seller.setBankAccounts(sellerBankAccounts);
    bankAccount.setSeller(seller);
    Order order = new Order();
    order.setId(Long.valueOf(RANDOM_ID));
    List<BasketProduct> userBasketProducts = new LinkedList<>();
    userBasketProducts.addAll(basket.getBasketProducts());

    when(creditCardRepository.findCreditCardById(card.getCreditCardId())).thenReturn(creditCard);
    when(creditCardRepository.findCreditCardsByUsersId(user.getId())).thenReturn(userCreditCards);
    when(basketRepository.findBasketByUserId(user.getId())).thenReturn(basket);
    when(productRepository.findProductsByIdIn(any())).thenReturn(productsFromDB);
    when(basketProductRepository.findBasketProductByBasketUserId(user.getId())).thenReturn(userBasketProducts);
    when(sellerRepository.findSellerById(product.getSeller().getId())).thenReturn(seller);
    when(productRepository.findProductById(product.getId())).thenReturn(product);
    when(orderRepository.save(any())).thenReturn(order);

    OrderRequestResponseDTO orderResponse = userServiceUnderTest.makeOrder(user, card);

    Assert.assertNotNull(orderResponse);
  }

  @Test
  public void refreshUserBasket(){

    Basket basket = new Basket();
    BasketProduct basketProduct = new BasketProduct();
    Product product = new Product();
    Set<BasketProduct> productsInBasket = new HashSet<>();

    basketProduct.setProduct(product);
    productsInBasket.add(basketProduct);
    basket.setBasketProducts(productsInBasket);
    user.setBasket(basket);

    userServiceUnderTest.refreshUserBasket(user);

    Assert.assertTrue(user.getBasket().getBasketProducts().isEmpty());
  }

  @Test
  public void isBusinessDisplayNameAvailable(){

    Seller seller = new Seller();
    seller.setBusinessDisplayName("Martin&Sons");
    String businessDisplayName = "Martin&Sons";

    when(sellerRepository.findSellerByBusinessDisplayName(businessDisplayName)).thenReturn(seller);

    Assert.assertFalse(userServiceUnderTest.isBusinessDisplayNameAvailable(businessDisplayName));
  }

  @Test(expected = BankAccountException.class)
  public void registerNewSeller_BankAccountUnavailable() throws SellerException, BankAccountException {

    SellerRegistrationDTO sellerRegistrationDTO = new SellerRegistrationDTO();
    BankAccountRegistrationDTO bankAccountRegistrationDTO = new BankAccountRegistrationDTO();
    bankAccountRegistrationDTO.setIban("abcdefg123456");
    sellerRegistrationDTO.setAccount(bankAccountRegistrationDTO);

    BankAccount bankAccountFromDB = new BankAccount();
    bankAccountFromDB.setIban("abcdefg123456");

    when(bankAccountRepository.findBankAccountByIban(bankAccountRegistrationDTO.getIban())).thenReturn(bankAccountFromDB);

    userServiceUnderTest.registerNewSeller(user, sellerRegistrationDTO);

  }

  @Test(expected = SellerException.class)
  public void registerNewSeller_BusinessDisplayNameUnavailable() throws SellerException, BankAccountException {

    SellerRegistrationDTO sellerRegistrationDTO = new SellerRegistrationDTO();
    BankAccountRegistrationDTO bankAccountRegistrationDTO = new BankAccountRegistrationDTO();
    bankAccountRegistrationDTO.setIban("abcdefg123456");
    sellerRegistrationDTO.setAccount(bankAccountRegistrationDTO);
    sellerRegistrationDTO.setBusinessDisplayName("Martin&Sons");

    Seller seller = new Seller();
    seller.setBusinessDisplayName("Martin&Sons");

    when(bankAccountRepository.findBankAccountByIban(bankAccountRegistrationDTO.getIban())).thenReturn(null);
    when(sellerRepository.findSellerByBusinessDisplayName(sellerRegistrationDTO.getBusinessDisplayName())).thenReturn(seller);

    userServiceUnderTest.registerNewSeller(user, sellerRegistrationDTO);
  }

  @Test
  public void registerNewSeller_ValidData() throws SellerException, BankAccountException {

    SellerRegistrationDTO sellerRegistrationDTO = new SellerRegistrationDTO();
    BankAccountRegistrationDTO bankAccountRegistrationDTO = new BankAccountRegistrationDTO();
    bankAccountRegistrationDTO.setIban("abcdefg123456");
    sellerRegistrationDTO.setAccount(bankAccountRegistrationDTO);
    sellerRegistrationDTO.setBusinessDisplayName("Martin&Sons");
    bankAccountRegistrationDTO.setBalance(RANDOM_BALANCE);

    Seller seller = new Seller();
    seller.setBusinessDisplayName("Martin&Sons");
    seller.setId(Long.valueOf(RANDOM_ID));

    when(bankAccountRepository.findBankAccountByIban(bankAccountRegistrationDTO.getIban())).thenReturn(null);
    when(sellerRepository.findSellerByBusinessDisplayName(sellerRegistrationDTO.getBusinessDisplayName())).thenReturn(null);
    when(sellerRepository.save(any())).thenReturn(seller);

    userServiceUnderTest.registerNewSeller(user, sellerRegistrationDTO);
  }

  @Test(expected = AlredySuchUserCreditCardException.class)
  public void addNewCreditCard_AlreadyHasThisCreditCard() throws AlredySuchUserCreditCardException {

    AddCreditCardDTO addCreditCardDTO = new AddCreditCardDTO();

    addCreditCardDTO.setCreditCardNumber("4024007158273622");
    addCreditCardDTO.setBalance(RANDOM_BALANCE);

    Set<CreditCard> userCreditCards = new HashSet<>();

    CreditCard creditCard = new CreditCard();
    creditCard.setCreditCardNumber("4024007158273622");
    creditCard.setId(Long.valueOf(RANDOM_ID));

    userCreditCards.add(creditCard);

    when(creditCardRepository.findCreditCardByCreditCardNumber(creditCard.getCreditCardNumber())).thenReturn(creditCard);
    when(creditCardRepository.findCreditCardsByUsersId(user.getId())).thenReturn(userCreditCards);

    userServiceUnderTest.addNewCreditCard(user, addCreditCardDTO);
  }

  @Test
  public void addNewCreditCard() throws AlredySuchUserCreditCardException {

    AddCreditCardDTO addCreditCardDTO = new AddCreditCardDTO();

    addCreditCardDTO.setCreditCardNumber("4024007158273622");
    addCreditCardDTO.setBalance(RANDOM_BALANCE);

    Set<CreditCard> userCreditCards = new HashSet<>();

    CreditCard creditCard = new CreditCard();
    creditCard.setCreditCardNumber("4024007158273622");

    userCreditCards.add(creditCard);

    when(creditCardRepository.findCreditCardByCreditCardNumber(creditCard.getCreditCardNumber())).thenReturn(creditCard);
    when(creditCardRepository.findCreditCardsByUsersId(user.getId())).thenReturn(userCreditCards);
    creditCard.setId(Long.valueOf(RANDOM_ID));
    when(creditCardRepository.save(any())).thenReturn(creditCard);

    userServiceUnderTest.addNewCreditCard(user, addCreditCardDTO);
  }

  @Test
  public void findUserAddresses(){

    List<Address> userAddresses = new LinkedList<>();
    Address address = new Address();
    userAddresses.add(address);
    City city = new City();
    city.setName("Random city name");

    when(addressRepository.findAddressesByUserId(user.getId())).thenReturn(userAddresses);
    when(cityRepository.findCityByAddressesContains(address)).thenReturn(city);

    List<AddressDisplayDTO> addressDisplayDTOS = userServiceUnderTest.findUserAddresses(user);

    Assert.assertFalse(addressDisplayDTOS.isEmpty());
  }

  @Test
  public void findUserCreditCards(){

    Set<CreditCard> userCreditCards = new HashSet<>();
    CreditCard creditCard = new CreditCard();
    userCreditCards.add(creditCard);

    when(creditCardRepository.findCreditCardsByUsersId(user.getId())).thenReturn(userCreditCards);

    Set<DisplayCreditCardDTO> displayCreditCardDTOS = userServiceUnderTest.findUserCreditCards(user);

    Assert.assertFalse(displayCreditCardDTOS.isEmpty());
  }

  @Test(expected = UserException.class)
  public void register_EmailTaken() throws UserException {

    UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
    User userFromDB = new User();

    when(userRepository.findUserByEmail(any())).thenReturn(userFromDB);

    userServiceUnderTest.register(userRegistrationDTO);
  }

  @Test
  public void register_ValidData() throws UserException {
    UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO();
    userRegistrationDTO.setPassword(VALID_PASSWORD);
    User newRegisteredUser = new User();
    newRegisteredUser.setId(Long.valueOf(RANDOM_ID));

    when(userRepository.findUserByEmail(any())).thenReturn(null);
    when(userRepository.save(any())).thenReturn(newRegisteredUser);

    userServiceUnderTest.register(userRegistrationDTO);
  }

  @Test
  public void addNewAddress(){

    AddNewAddressDTO addNewAddressDTO = new AddNewAddressDTO();
    addNewAddressDTO.setCountryName("Random country name");
    addNewAddressDTO.setCityName("Random city name");
    addNewAddressDTO.setAddressTypeName("random address type name");
    addNewAddressDTO.setStreetName("Random street name");
    Address addressAddedToDB = new Address();
    addressAddedToDB.setId(Long.valueOf(RANDOM_ID));

    when(countryRepository.findCountryByName(addNewAddressDTO.getCountryName())).thenReturn(null);
    when(cityRepository.findCityByName(addNewAddressDTO.getCityName())).thenReturn(null);
    when(addressTypeRepository.findAddressTypeByName(addNewAddressDTO.getAddressTypeName())).thenReturn(null);
    when(addressRepository.save(any())).thenReturn(addressAddedToDB);

    Long newAddressId = userServiceUnderTest.addNewAddress(user, addNewAddressDTO);

    Assert.assertEquals(newAddressId, addressAddedToDB.getId());

  }

}

package com.shop.demo.service;

import com.shop.demo.exception.NoSuchProductException;
import com.shop.demo.exception.NotEnoughQuantityException;
import com.shop.demo.model.entity.Basket;
import com.shop.demo.model.entity.BasketProduct;
import com.shop.demo.model.entity.Product;
import com.shop.demo.model.entity.User;
import com.shop.demo.repository.BasketProductRepository;
import com.shop.demo.repository.ProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class BasketServiceTest {

  public static final int ID = 1;
  public static final int QUANTITY = 2;
  @Mock
  private BasketProductRepository basketProductRepository;
  @Mock
  private ProductRepository productRepository;
  @InjectMocks
  private BasketService basketServiceUnderTest;

  private User user;

  private Basket basket;

  @Before
  public void setUp(){
    initMocks(this);
    this.user = new User();
    this.basket = new Basket();
    basket.setId(Long.valueOf(ID));
  }

  @Test(expected = NoSuchProductException.class)
  public void addProductToBasket_NoSuchProduct() throws NotEnoughQuantityException, NoSuchProductException {

    Integer productToAddToBasketQuantity = Integer.valueOf(ID);
    Long productAddToBasketId = Long.valueOf(ID);

    when(productRepository.findProductById(productAddToBasketId)).thenReturn(null);

    basketServiceUnderTest.addProductToBasket(user, productToAddToBasketQuantity, productAddToBasketId);
  }

  @Test(expected = NotEnoughQuantityException.class)
  public void addProductToBasket_NotEnoughQuantity() throws NotEnoughQuantityException, NoSuchProductException {

    Integer productToAddToBasketQuantity = Integer.valueOf(QUANTITY);
    Long productAddToBasketId = Long.valueOf(ID);
    Product productFromDB = new Product();
    productFromDB.setQuantity(Integer.valueOf(ID));

    when(productRepository.findProductById(productAddToBasketId)).thenReturn(productFromDB);

    basketServiceUnderTest.addProductToBasket(user, productToAddToBasketQuantity, productAddToBasketId);
  }

  @Test
  public void addProductToBasket_ValidData() throws NotEnoughQuantityException, NoSuchProductException {

    Integer productToAddToBasketQuantity = Integer.valueOf(ID);
    Long productAddToBasketId = Long.valueOf(ID);
    Product productFromDB = new Product();
    productFromDB.setQuantity(Integer.valueOf(QUANTITY));
    user.setBasket(basket);

    when(productRepository.findProductById(productAddToBasketId)).thenReturn(productFromDB);

    basketServiceUnderTest.addProductToBasket(user, productToAddToBasketQuantity, productAddToBasketId);
  }

  @Test(expected = NoSuchProductException.class)
  public void removeProductFromUserBasket_NoSuchProductException() throws NoSuchProductException {
    Long productId = Long.valueOf(ID);

    when(basketProductRepository.findBasketProductByBasket_IdAndAndProduct_Id(basket.getId(), productId)).thenReturn(null);

    basketServiceUnderTest.removeProductFromUserBasket(productId, basket);
  }

  @Test
  public void removeProductFromUserBasket_ValidData() throws NoSuchProductException {
    Long productId = Long.valueOf(ID);
    BasketProduct basketProduct = new BasketProduct();

    when(basketProductRepository.findBasketProductByBasket_IdAndAndProduct_Id(basket.getId(), productId)).thenReturn(basketProduct);

    basketServiceUnderTest.removeProductFromUserBasket(productId, basket);
  }

}

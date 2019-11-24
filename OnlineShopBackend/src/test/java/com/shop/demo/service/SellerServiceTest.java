package com.shop.demo.service;

import com.shop.demo.exception.SellerException;
import com.shop.demo.model.dto.ProductAddToSellDTO;
import com.shop.demo.model.entity.Category;
import com.shop.demo.model.entity.Product;
import com.shop.demo.model.entity.Seller;
import com.shop.demo.model.entity.User;
import com.shop.demo.repository.CategoryRepository;
import com.shop.demo.repository.ProductRepository;
import com.shop.demo.repository.SellerRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class SellerServiceTest {
  public static final int ID = 1;
  @Mock
  private SellerRepository sellerRepository;
  @Mock
  private ProductRepository productRepository;
  @Mock
  private CategoryRepository categoryRepository;
  @InjectMocks
  private SellerService sellerServiceUnderTest;

  private Seller seller;
  private User user;
  private ProductAddToSellDTO productAddToSellDTO;

  @Before
  public void setUp(){
    initMocks(this);
    this.seller = new Seller();
    this.user = new User();
    this.productAddToSellDTO = new ProductAddToSellDTO();
    productAddToSellDTO.setSellerId(Long.valueOf(ID));
  }

  @Test(expected = SellerException.class)
  public void addProductToSell_NoSuchsellerProfile() throws SellerException {

    when(sellerRepository.findSellerById(productAddToSellDTO.getSellerId())).thenReturn(null);

    sellerServiceUnderTest.addProductForSell(user, productAddToSellDTO);
  }

  @Test(expected = SellerException.class)
  public void addproductToSell_SellerDoesntMatchUser() throws SellerException {

    when(sellerRepository.findSellerById(productAddToSellDTO.getSellerId())).thenReturn(seller);

    sellerServiceUnderTest.addProductForSell(user, productAddToSellDTO);
  }

  @Test(expected = SellerException.class)
  public void addproductToSell_SellerAlreadyHasTheProductForSell() throws SellerException {

    Set<Seller> userSellerProfiles = new HashSet<>();
    userSellerProfiles.add(seller);
    user.setSellers(userSellerProfiles);
    Product product = new Product();
    Set<Product> sellerProducts = new HashSet<>();
    sellerProducts.add(product);

    when(sellerRepository.findSellerById(productAddToSellDTO.getSellerId())).thenReturn(seller);
    when(productRepository.findProductBySeller_IdAndAndName(productAddToSellDTO.getSellerId(), productAddToSellDTO.getName())).thenReturn(product);

    sellerServiceUnderTest.addProductForSell(user, productAddToSellDTO);
  }

  @Test
  public void addProductTosell_ValidData() throws SellerException {

    productAddToSellDTO.setCategoryId(Long.valueOf(ID));
    Set<Seller> userSellerProfiles = new HashSet<>();
    userSellerProfiles.add(seller);
    user.setSellers(userSellerProfiles);
    Product product = new Product();
    product.setId(Long.valueOf(ID));
    Set<Product> sellerProducts = new HashSet<>();
    sellerProducts.add(product);
    Category category = new Category();

    when(sellerRepository.findSellerById(productAddToSellDTO.getSellerId())).thenReturn(seller);
    when(productRepository.findProductBySeller_IdAndAndName(productAddToSellDTO.getSellerId(), productAddToSellDTO.getName())).thenReturn(null);
    when(categoryRepository.findCategoryById(productAddToSellDTO.getCategoryId())).thenReturn(category);
    when(productRepository.save(any())).thenReturn(product);

    long newProductId = sellerServiceUnderTest.addProductForSell(user, productAddToSellDTO);

    Assert.assertEquals(newProductId, ID);
  }
}

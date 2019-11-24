package com.shop.demo.controller;

import com.shop.demo.exception.NoSuchProductException;
import com.shop.demo.model.dto.ProductDetailsDTO;
import com.shop.demo.model.dto.ProductListDTO;
import com.shop.demo.model.entity.Product;
import com.shop.demo.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.LinkedList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sun.nio.cs.Surrogate.is;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private WebApplicationContext wac;
  @MockBean
  private ProductService productService;
  @InjectMocks
  private ProductController productControllerUnderTest;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    initMocks(this);
  }
  @Test
  public void getProductsFromCategory_NoSearchCriterias() throws Exception {

    List<ProductListDTO> products = new LinkedList<>();
    ProductListDTO product = new ProductListDTO();
    product.setId(Long.valueOf(1));
    product.setName("name");
    product.setPrice(123.4);
    products.add(product);
    when(productService.getFilteredSortedProducts(null, null , null)).thenReturn(products);
    mockMvc.perform(MockMvcRequestBuilders.get("/products"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("name"))
        .andExpect(jsonPath("$[0].price").value(123.4));
  }

  @Test
  public void getProduct_ProductNotFound() throws Exception {

    when(productService.findProductById(any())).thenThrow(NoSuchProductException.class);
    mockMvc.perform(MockMvcRequestBuilders.get("/product/1"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getProduct() throws Exception {
    ProductDetailsDTO product = new ProductDetailsDTO();
    product.setId(Long.valueOf(1));
    product.setDescription("Something");
    product.setName("name");
    product.setPrice(123.4);

    when(productService.findProductById(any())).thenReturn(product);
    mockMvc.perform(MockMvcRequestBuilders.get("/product/1"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("name"))
        .andExpect(jsonPath("$.description").value("Something"))
        .andExpect(jsonPath("$.price").value(123.4));
  }
}

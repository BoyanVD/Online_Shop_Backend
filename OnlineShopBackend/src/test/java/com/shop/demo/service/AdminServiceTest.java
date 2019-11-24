package com.shop.demo.service;

import com.shop.demo.exception.CategoryException;
import com.shop.demo.exception.NoSuchProductException;
import com.shop.demo.model.dto.AddCategoryDTO;
import com.shop.demo.model.entity.Category;
import com.shop.demo.model.entity.Product;
import com.shop.demo.repository.BasketProductRepository;
import com.shop.demo.repository.CategoryRepository;
import com.shop.demo.repository.ProductRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

public class AdminServiceTest {

  public static final int ID = 1;
  @Mock
  private CategoryRepository categoryRepository;
  @Mock
  private BasketProductRepository basketProductRepository;
  @Mock
  private ProductRepository productRepository;
  @InjectMocks
  private AdminService adminServiceUnderTest;

  private AddCategoryDTO addCategoryDTO;

  @Before
  public void setUp(){
    initMocks(this);
    this.addCategoryDTO = new AddCategoryDTO();
    addCategoryDTO.setCategoryName("Random category name");
  }

  @Test(expected = CategoryException.class)
  public void addCategory_AlreadySuchCategory() throws CategoryException {

    Category categoryFromDB = new Category();

    when(categoryRepository.findCategoryByName(addCategoryDTO.getCategoryName())).thenReturn(categoryFromDB);

    adminServiceUnderTest.addCategory(addCategoryDTO);
  }

  @Test(expected = CategoryException.class)
  public void addCategory_AlreadySuchParentCategory() throws CategoryException {

    addCategoryDTO.setParentCategoryId(Long.valueOf(ID));

    Category categoryFromDB = new Category();

    when(categoryRepository.findCategoryByName(addCategoryDTO.getCategoryName())).thenReturn(null);
    when(categoryRepository.findCategoryById(addCategoryDTO.getParentCategoryId())).thenReturn(null);

    adminServiceUnderTest.addCategory(addCategoryDTO);
  }

  @Test
  public void addCategory_ValidData() throws CategoryException {

    addCategoryDTO.setParentCategoryId(Long.valueOf(ID));

    Category newAddedCategory = new Category();
    newAddedCategory.setId(Long.valueOf(ID));

    Category parentCategory = new Category();

    when(categoryRepository.findCategoryByName(addCategoryDTO.getCategoryName())).thenReturn(null);
    when(categoryRepository.findCategoryById(addCategoryDTO.getParentCategoryId())).thenReturn(parentCategory);
    when(categoryRepository.save(any())).thenReturn(newAddedCategory);

    Long newCategoryId = adminServiceUnderTest.addCategory(addCategoryDTO);

    Assert.assertNotNull(newCategoryId);
  }

  @Test(expected = NoSuchProductException.class)
  public void deleteProduct_NoSuchProduct() throws NoSuchProductException {

    Long productToDeleteId = Long.valueOf(ID);

    when(productRepository.findProductById(productToDeleteId)).thenReturn(null);

    adminServiceUnderTest.deleteProduct(productToDeleteId);
  }

  @Test
  public void deleteProduct_ValidData() throws NoSuchProductException {

    Long productToDeleteId = Long.valueOf(ID);
    Product product = new Product();

    when(productRepository.findProductById(productToDeleteId)).thenReturn(product);

    adminServiceUnderTest.deleteProduct(productToDeleteId);
  }
}

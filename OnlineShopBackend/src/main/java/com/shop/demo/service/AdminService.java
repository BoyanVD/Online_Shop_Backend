package com.shop.demo.service;

import com.shop.demo.exception.CategoryException;
import com.shop.demo.exception.NoSuchProductException;
import com.shop.demo.exception.ProductException;
import com.shop.demo.model.dto.AddCategoryDTO;
import com.shop.demo.model.entity.Category;
import com.shop.demo.model.entity.Product;
import com.shop.demo.repository.BasketProductRepository;
import com.shop.demo.repository.CategoryRepository;
import com.shop.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class AdminService {

  private final CategoryRepository categoryRepository;
  private final BasketProductRepository basketProductRepository;
  private final ProductRepository productRepository;

  @Autowired
  public AdminService(CategoryRepository categoryRepository,
                      BasketProductRepository basketProductRepository,
                      ProductRepository productRepository) {
    this.categoryRepository = categoryRepository;
    this.basketProductRepository = basketProductRepository;
    this.productRepository = productRepository;
  }


  /**
   * This method adds new category in DB and returns the generated ID
   * @param  category
   * @return Long addedCategoryId
   * @exception CategoryException
   */
  public Long addCategory(AddCategoryDTO category) throws CategoryException  {

    Category categoryFromDB = categoryRepository.findCategoryByName(category.getCategoryName());
    if(categoryFromDB != null){
      throw new CategoryException("Already such category !!!");
    }
    categoryFromDB = new Category();
    categoryFromDB.setName(category.getCategoryName());
    if(category.getParentCategoryId() != null) {
      Category parentCategory = categoryRepository.findCategoryById(category.getParentCategoryId());
      if(parentCategory == null){
        throw new CategoryException("No such parent category !!!");
      }
      categoryFromDB.setParentCategory(parentCategory);
    }
    Long newCategoryId = categoryRepository.save(categoryFromDB).getId();
    return newCategoryId;
  }

  /**
   * This method deletes Product from DB
   * @param productId
   * @return Nothing.
   * @exception ProductException
   */
  @Transactional
  public void deleteProduct(Long productId) throws NoSuchProductException {
    Product product = productRepository.findProductById(productId);
    if(product == null){
      throw new NoSuchProductException("There is no such product !!! ");
    }
    basketProductRepository.deleteBasketProductsByProduct_Id(productId);
    productRepository.delete(product);
  }
}

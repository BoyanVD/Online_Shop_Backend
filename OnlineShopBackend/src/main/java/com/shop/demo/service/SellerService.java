package com.shop.demo.service;

import com.shop.demo.exception.NoSuchProductException;
import com.shop.demo.exception.SellerException;
import com.shop.demo.model.dto.DeleteProductDTO;
import com.shop.demo.model.dto.ProductAddToSellDTO;
import com.shop.demo.model.dto.ProductUpdateDTO;
import com.shop.demo.model.entity.Category;
import com.shop.demo.model.entity.Product;
import com.shop.demo.model.entity.Seller;
import com.shop.demo.model.entity.User;
import com.shop.demo.repository.CategoryRepository;
import com.shop.demo.repository.ProductRepository;
import com.shop.demo.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Set;

@Component
public class SellerService {

  private final SellerRepository sellerRepository;
  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;

  @Autowired
  public SellerService(SellerRepository sellerRepository,
                       ProductRepository productRepository,
                       CategoryRepository categoryRepository) {
    this.sellerRepository = sellerRepository;
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
  }

  /**
   * This method adds product for sell
   * @param user
   * @param product
   * @return Long newProductId
   * @throws SellerException
   */
  public Long addProductForSell(User user, ProductAddToSellDTO product) throws SellerException {

    Seller seller = sellerRepository.findSellerById(product.getSellerId());
    this.validateSeller(user, seller);

    Product sellerProductFromDB = productRepository.findProductBySeller_IdAndAndName(product.getSellerId(), product.getName());
    if(sellerProductFromDB != null){
      throw new SellerException("Seller already has the product for sell !!!");
    }

    Category category = categoryRepository.findCategoryById(product.getCategoryId());
    Product newProductForSell = new Product(product, category, seller);

    Long newProductId = productRepository.save(newProductForSell).getId();

    return newProductId;
  }

  public void validateSeller(User user, Seller seller) throws SellerException {
    if(seller == null){
      throw new SellerException("No such Seller profile");
    }
    Set<Seller> userSellerProfiles = user.getSellers();
    if(userSellerProfiles == null || !userSellerProfiles.contains(seller)){
      throw new SellerException("Seller profile doesnt match user !!!");
    }
  }

  /**
   * This method deletes product for sell
   * @param user
   * @param product
   * @throws SellerException
   * @throws NoSuchProductException
   */
  @Transactional
  public void deleteProduct(User user, DeleteProductDTO product) throws SellerException, NoSuchProductException {

    Seller seller = sellerRepository.findSellerById(product.getSellerId());
    this.validateSeller(user, seller);
    if(productRepository.deleteByIdAndSeller_Id(product.getProductId(), product.getSellerId()) == 0){
      throw new NoSuchProductException("There is no such product in seller's products list !!!");
    }
  }

  /**
   * This method updates product info
   * @param user
   * @param product
   * @throws SellerException
   * @throws NoSuchProductException
   */
  public void updateProduct(User user, ProductUpdateDTO product) throws NoSuchProductException, SellerException {

    Seller seller = sellerRepository.findSellerById(product.getSellerId());
    this.validateSeller(user, seller);

    Product productFromDB = productRepository.findProductById(product.getId());
    if(productFromDB == null){
      throw new NoSuchProductException("There is no such product !!!");
    }
    if(!productFromDB.getSeller().equals(seller)){
      throw new SellerException("The seller doesn't have the product in his products list !!!");
    }

    productFromDB.setName(product.getName());
    productFromDB.setDescription(product.getDescription());
    productFromDB.setPrice(product.getPrice());
    productFromDB.setQuantity(product.getQuantity());

    productRepository.save(productFromDB);

  }
}

package com.shop.demo.service;

import com.shop.demo.exception.NoSuchProductException;
import com.shop.demo.model.dto.ProductDetailsDTO;
import com.shop.demo.model.dto.ProductListDTO;
import com.shop.demo.model.entity.Category;
import com.shop.demo.model.entity.Product;
import com.shop.demo.repository.CategoryRepository;
import com.shop.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ProductService {

  private final ProductRepository productRepository;
  private final CategoryRepository categoryRepository;
  private final EntityManager entityManager;

  @Autowired
  public ProductService(ProductRepository productRepository,
                        CategoryRepository categoryRepository,
                        EntityManager entityManager) {
    this.productRepository = productRepository;
    this.categoryRepository = categoryRepository;
    this.entityManager = entityManager;
  }

  /**
   * This method finds and returns product details for DB by id given
   * @param id
   * @return ProductDetailsDTO product
   * @throws NoSuchProductException
   */
  public ProductDetailsDTO findProductById(Long id) throws NoSuchProductException {
    Product product = productRepository.findProductById(id);
    if(product == null){
      throw new NoSuchProductException("No such product!");
    }
    ProductDetailsDTO productDetailsDTO = new ProductDetailsDTO(product);
    return productDetailsDTO;
  }

  /**
   * Method filters products according to their category and name. It can also provide the product list in ascendind/descending order.
   * @param productName
   * @param categoryId
   * @param sortBy
   * @return List<ProductListDTO> products
   * @throws NoSuchProductException
   */
  public List<ProductListDTO> getFilteredSortedProducts(String productName, Long categoryId, String sortBy) throws NoSuchProductException {

    Map<String, Object> paramaterMap = new HashMap<>();
    List<String> whereCause = new ArrayList<>();

    StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append("select p from Product p ");

    if (categoryId != null){
      Category category = categoryRepository.findCategoryById(categoryId);
      whereCause.add(" p.category =:category ");
      paramaterMap.put("category", category);
    }
    if (productName != null){
      whereCause.add(" p.name like :productName ");
      paramaterMap.put("productName", productName);
    }
    if(!whereCause.isEmpty()) {
      queryBuilder.append(" where " + String.join( " and ", whereCause));
    }

    if(sortBy != null) {
      switch (sortBy) {
        case "priceAscending":
          queryBuilder.append(" order by p.price ");
          break;
        case "priceDescending":
          queryBuilder.append(" order by p.price desc ");
          break;
      }
    }

    Query jpaQuery = entityManager.createQuery(queryBuilder.toString());

    for (String key : paramaterMap.keySet()) {
      if (key.equals("productName")) {
        jpaQuery.setParameter(key, "%" + paramaterMap.get(key) + "%");
      } else {
        jpaQuery.setParameter(key, paramaterMap.get(key));
      }
    }
    List<Product> products = jpaQuery.getResultList();
    if (products.isEmpty()) {
      throw new NoSuchProductException("Can't find any matching products");
    }

    List<ProductListDTO> displayProducts = products.stream().map(p -> new ProductListDTO(p)).collect(Collectors.toList());

      return displayProducts;
    }

}

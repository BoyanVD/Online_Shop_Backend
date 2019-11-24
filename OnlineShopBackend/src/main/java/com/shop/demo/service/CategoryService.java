package com.shop.demo.service;

import com.shop.demo.model.entity.Category;
import com.shop.demo.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryService {

  private final CategoryRepository categoryRepository;

  @Autowired
  public CategoryService(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  /**
   * This method returns all Categories from DB
   *
   * @return List<Category> allCategories
   */
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  /**
   * This method returns all Departments from DB
   *
   * @return List<Category. allDepartments
   */
  public List<Category> findAllDepartments() {
    return categoryRepository.findAllByParentCategoryIsNull();
  }

  public List<Category> getAllSubcategories(Long parentCategotyId){
    return categoryRepository.findCategoriesByParentCategory_Id(parentCategotyId);
  }
}

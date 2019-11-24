package com.shop.demo.controller;

import com.shop.demo.model.entity.Category;
import com.shop.demo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  @GetMapping("/categories")
  public List<Category> getAllCategories() {
    return this.categoryService.getAllCategories();
  }

  @GetMapping("/departments")
  public List<Category> getDepartments() {
    List<Category> list =  this.categoryService.findAllDepartments();
    return list;
  }

  @GetMapping("/subcategories/{id}")
  public List<Category> getAllSubcategories(@PathVariable Long id){
    return this.categoryService.getAllSubcategories(id);
  }
}

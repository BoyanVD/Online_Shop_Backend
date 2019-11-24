package com.shop.demo.repository;

import com.shop.demo.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

  public List<Category> findAllByParentCategoryIsNull();

  public List<Category> findCategoriesByParentCategory_Id(Long id);

  public Category findCategoryByName(String name);

  public Category findCategoryById(Long id);
}

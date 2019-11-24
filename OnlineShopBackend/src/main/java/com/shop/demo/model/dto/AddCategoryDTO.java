package com.shop.demo.model.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddCategoryDTO {

  @NotBlank(message = "Category name can't be blank or null!")
  private String categoryName;

  @Positive(message = "Parent category id can't be negative or zero ")
  private Long parentCategoryId;
}

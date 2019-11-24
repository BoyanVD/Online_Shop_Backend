package com.shop.demo.model.dto;

import com.shop.demo.model.entity.GiftCard;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDTO {

  @NotBlank(message = "Invalid name!")
  private String name;

  @Email(message = "Invalid email!")
  private String email;
}

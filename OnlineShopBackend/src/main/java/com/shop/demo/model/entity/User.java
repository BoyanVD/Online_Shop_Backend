package com.shop.demo.model.entity;


import com.shop.demo.exception.UserException;
import com.shop.demo.model.dto.UserRegistrationDTO;
import com.shop.demo.service.UserService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "email")
  private String email;

  @Column(name = "name")
  private String name;

  @Column(name = "is_admin")
  @Type(type="boolean")
  private boolean isAdmin;

  @Column(name = "password")
  private String password;

  @JsonIgnore
  @EqualsAndHashCode.Exclude
  @ManyToMany(targetEntity = CreditCard.class)
  @JoinTable(name = "user_credit_card",
      joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "credit_card_id", referencedColumnName = "id"),
      uniqueConstraints = {
          @UniqueConstraint(columnNames = {
              "user_id",
              "credit_card_id"
          })
      })
  private Set<CreditCard> creditCards;

  @EqualsAndHashCode.Exclude
  @JsonIgnore
  @OneToMany(mappedBy = "user")
  private Set<GiftCard> giftCards;

  @JsonIgnore
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "user")
  private Set<Order> orders;

  @JsonIgnore
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "user")
  private Set<Seller> sellers;

  @JsonIgnore
  @OneToOne
  @EqualsAndHashCode.Exclude
  private Basket basket;

  public User(UserRegistrationDTO user) throws UserException {
    this.email = user.getEmail();
    this.name = user.getName();
    this.password = UserService.stringToSha1(user.getPassword());
    this.setAdmin(false);
  }
}

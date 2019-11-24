package com.shop.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "gift_card")
public class GiftCard {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private double balance;

  @EqualsAndHashCode.Exclude
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

}

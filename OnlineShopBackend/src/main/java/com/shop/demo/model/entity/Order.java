package com.shop.demo.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_table")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private LocalDate orderDate;
  private LocalDate deliveryDate;

  @JsonIgnore
  @EqualsAndHashCode.Exclude
  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;
}

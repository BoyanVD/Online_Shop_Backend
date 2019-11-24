package com.shop.demo.repository;

import com.shop.demo.model.entity.Product;
import com.shop.demo.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  public User findUserByEmail(String email);
}

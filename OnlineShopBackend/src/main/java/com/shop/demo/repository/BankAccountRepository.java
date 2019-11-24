package com.shop.demo.repository;

import com.shop.demo.model.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

  public BankAccount findBankAccountByIban(String iban);
}

package com.madar.bankDemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.madar.bankDemo.model.Bank;

public interface BankRepository extends JpaRepository<Bank, Long> {
	Bank findById(long id);
}
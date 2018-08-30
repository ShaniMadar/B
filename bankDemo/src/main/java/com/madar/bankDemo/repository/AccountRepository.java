package com.madar.bankDemo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.madar.bankDemo.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findById(long id);
	
  @Query(nativeQuery=true, value="SELECT * FROM ACCOUNT a WHERE a.branch_id = :branch_id")
    List<Account> findAccountByBranchId(@Param("branch_id") long branch_id);
  
  @Modifying
  @Transactional 
  @Query(nativeQuery=true, value="DELETE FROM ACCOUNT a WHERE a.branch_id = :branch_id")
  	void deleteAccountByBranchId(@Param("branch_id") long branch_id);
  
  @Modifying(clearAutomatically = true)
  @Query(nativeQuery=true, value="UPDATE account a SET a.branch_id = :branch_id, a.balance = :balance WHERE a.id = :id")
  	void updateAccountById(@Param("id") long id, @Param("branch_id") long branch_id, @Param("balance") float balance);
}
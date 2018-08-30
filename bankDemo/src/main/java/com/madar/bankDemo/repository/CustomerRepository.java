package com.madar.bankDemo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.madar.bankDemo.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	Customer findById(long id);
	
  @Query(nativeQuery=true, value="SELECT * FROM CUSTOMER c WHERE c.account_id = :account_id")
    List<Customer> findCustomersByAccountId(@Param("account_id") long account_id);
  
  @Modifying
  @Transactional 
  @Query(nativeQuery=true, value="DELETE FROM customer b WHERE b.account_id = :account_id")
  	void deleteCustomersByAccountId(@Param("account_id") long account_id);
  
  @Modifying(clearAutomatically = true)
  @Query(nativeQuery=true, value="UPDATE customer a SET a.account_id = :account_id, a.firstName = :firstname, a.lastname = :lastname, a.gender = :gender WHERE a.id = :id")
  	void updateCustomerById(@Param("id") long id, @Param("account_id") long account_id, @Param("firstname") String firstname, @Param("lastname") String lastname, @Param("gender") String gender);
}
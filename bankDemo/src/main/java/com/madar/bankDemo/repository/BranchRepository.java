package com.madar.bankDemo.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.madar.bankDemo.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {
	Branch findById(long id);
	
  @Query(nativeQuery=true, value="SELECT * FROM BRANCH b WHERE b.bank_id = :bank_id")
    List<Branch> findBranchByBankId(@Param("bank_id") long bank_id);
  
  @Modifying
  @Transactional 
  @Query(nativeQuery=true, value="DELETE FROM BRANCH b WHERE b.bank_id = :bank_id")
  	void deleteBranchByBankId(@Param("bank_id") long bank_id);
  
  @Modifying(clearAutomatically = true)
  @Query(nativeQuery=true, value="UPDATE BRANCH b SET b.adress = :adress WHERE b.id = :id")
  	void updateBranchById(@Param("id") long id, @Param("adress") String adress);
}
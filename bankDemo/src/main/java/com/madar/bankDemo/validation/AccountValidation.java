package com.madar.bankDemo.validation;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.madar.bankDemo.repository.AccountRepository;
import com.madar.bankDemo.repository.BranchRepository;

@Component
public class AccountValidation implements ValidationInterface{
	
    @Autowired
    AccountRepository accountRep;
    @Autowired
    BranchRepository branchRep;
    
    private static final Logger logger = LoggerFactory.getLogger(AccountValidation.class);

	@Override
	public boolean isValidInsert(long id, long branch_id) {
    	logger.info("validating insert of new branch");
    	if(!branchRep.existsById(branch_id)) {
    		logger.warn("Branch "+branch_id+" Doesn't exist in the Database, validation failed");
    		return false;
    	}
    	if(accountRep.existsById(id)) {
    		logger.warn("Account "+id+" already exists in the database, validation failed");
    		return false;
    	}
    	logger.info("Validation sucessful");
    	return true;
	}

	@Override
	public boolean isValidUpdate(long id, long branch_id) {
    	logger.info("Validating update of branch");
    	if(!branchRep.existsById(branch_id)) {
    		logger.warn("Branch "+branch_id+" Doesn't exist in the Database, validation failed");
    		return false;
    	}
    	if(!accountRep.existsById(id)) {
    		logger.warn("Account "+id+" doesn't exist in the database, validation failed");
    		return false;
    	}
    	logger.info("Validation sucessful");
    	return true;
	}

	@Override
	public boolean isExist(long id) {
    	if(!accountRep.existsById(id)) {
    		logger.warn("Account "+id+" doesn't exist in the database, validation failed");
    		return false;
    	}
    	logger.info("Validation sucessful");
    	return true;
	}

	@Override
	public boolean isParentExist(long id) {
    	if(!branchRep.existsById(id)) {
    		logger.warn("Branch "+id+" doesn't exist in the database,  validation failed");
    		return false;
    	}
    	logger.info("Validation sucessful");
    	return true;
	}
    

}

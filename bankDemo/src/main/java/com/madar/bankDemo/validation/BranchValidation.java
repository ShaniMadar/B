package com.madar.bankDemo.validation;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.madar.bankDemo.repository.BankRepository;
import com.madar.bankDemo.repository.BranchRepository;

@Component
public class BranchValidation implements ValidationInterface{
	
    @Autowired
    BranchRepository branchRep;
    @Autowired
    BankRepository bankRep;
    
    private static final Logger logger = LoggerFactory.getLogger(BranchValidation.class);

	@Override
	public boolean isValidInsert(long id, long bank_id) {
    	logger.info("validating insert of new branch");
    	if(!bankRep.existsById(bank_id)) {
    		logger.warn("Bank "+bank_id+" Doesn't exist in the Database, validation failed");
    		return false;
    	}
    	if(branchRep.existsById(id)) {
    		logger.warn("Branch "+id+" already exists in the database, validation failed");
    		return false;
    	}
    	logger.info("Validation sucessful");
    	return true;
	}

	@Override
	public boolean isValidUpdate(long id, long bank_id) {
    	logger.info("Validating update of branch");
    	if(!bankRep.existsById(bank_id)) {
    		logger.warn("Bank "+bank_id+" Doesn't exist in the Database, validation failed");
    		return false;
    	}
    	if(!branchRep.existsById(id)) {
    		logger.warn("Branch "+id+" doesn't exist in the database, validation failed");
    		return false;
    	}
    	logger.info("Validation sucessful");
    	return true;
	}

	@Override
	public boolean isExist(long id) {
    	if(!branchRep.existsById(id)) {
    		logger.warn("Branch "+id+" doesn't exist in the database, validation failed");
    		return false;
    	}
    	logger.info("Validation sucessful");
    	return true;
	}

	@Override
	public boolean isParentExist(long id) {
    	if(!bankRep.existsById(id)) {
    		logger.warn("Bank "+id+" doesn't exist in the database,  validation failed");
    		return false;
    	}
    	logger.info("Validation sucessful");
    	return true;
	}
    

}

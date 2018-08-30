package com.madar.bankDemo.validation;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.madar.bankDemo.repository.AccountRepository;
import com.madar.bankDemo.repository.CustomerRepository;

@Component
public class CustomerValidation implements ValidationInterface{
	
    @Autowired
    CustomerRepository customerRep;
    @Autowired
    AccountRepository accountRep;
    
    private static final Logger logger = LoggerFactory.getLogger(CustomerValidation.class);

	@Override
	public boolean isValidInsert(long id, long account_id) {
    	logger.info("validating insert of new Customer");
    	if(!accountRep.existsById(account_id)) {
    		logger.warn("Account "+account_id+" Doesn't exist in the Database, validation failed");
    		return false;
    	}
    	if(customerRep.existsById(id)) {
    		logger.warn("Customer "+id+" already exists in the database, validation failed");
    		return false;
    	}
    	logger.info("Validation sucessful");
    	return true;
	}

	@Override
	public boolean isValidUpdate(long id, long account_id) {
    	logger.info("Validating update of Customer");
    	if(!accountRep.existsById(account_id)) {
    		logger.warn("Account "+account_id+" Doesn't exist in the Database, validation failed");
    		return false;
    	}
    	if(!customerRep.existsById(id)) {
    		logger.warn("Customer "+id+" doesn't exist in the database, validation failed");
    		return false;
    	}
    	logger.info("Validation sucessful");
    	return true;
	}

	@Override
	public boolean isExist(long id) {
    	if(!customerRep.existsById(id)) {
    		logger.warn("Customer "+id+" doesn't exist in the database,  validation failed");
    		return false;
    	}
    	logger.info("Validation sucessful");
    	return true;
	}

	@Override
	public boolean isParentExist(long id) {
    	if(!accountRep.existsById(id)) {
    		logger.warn("Account "+id+" doesn't exist in the database,  validation failed");
    		return false;
    	}
    	logger.info("Validation sucessful");
    	return true;
	}
    

}

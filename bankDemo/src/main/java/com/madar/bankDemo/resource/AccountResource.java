package com.madar.bankDemo.resource;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.madar.bankDemo.cacheInterface.AccountCacheInterface;
import com.madar.bankDemo.model.Account;
import com.madar.bankDemo.model.Branch;
import com.madar.bankDemo.repository.AccountRepository;
import com.madar.bankDemo.repository.BranchRepository;
import com.madar.bankDemo.validation.AccountValidation;

@RestController
@RequestMapping(value = "/accounts")
public class AccountResource {
    @Autowired
    AccountCacheInterface accountCache;
    @Autowired
    BranchRepository branchRep;
    @Autowired
    AccountRepository accountRep;
    @Autowired
    AccountValidation validator;
    

    private static final Logger logger = LoggerFactory.getLogger(AccountResource.class);
    
    @GetMapping(value = "/{id}")
    public Account getAccount(@PathVariable final long id) {
    	logger.info("Retreveing Account Data From Database by account id:" + id);
        return accountCache.getAccount(id);
    }
    
    @GetMapping(value = "/all")
    public ArrayList<Account> getAllAccounts() {
    	logger.info("Retreveing accounts Data From Database");
        return (ArrayList<Account>) accountRep.findAll();
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Account addNewAccount(
    		@RequestParam("id") long id,
    		@RequestParam("balance") float balance,
    		@RequestParam("branch_id") long branch_id
    		){
    	logger.info("Insert new account request");
    	logger.info("Validating insert account request...");
    	if(!validator.isValidInsert(id, branch_id)) {
    		logger.info("validation failed");
    		return null;
    	}
    	Branch branch = branchRep.findById(branch_id);
    	logger.info("Inserting account to database.");
    	Account account = accountCache.save(new Account(id,balance,branch), id);
    	if(account!= null) {
	    	logger.info("Account Created Successfuly: " + id);
	    	return account;
    	}
    	logger.info("An error occurred, account was not created");
    	return null;
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Account updateAccount(
    		@RequestParam("id") long id,
    		@RequestParam("balance") float balance,
    		@RequestParam("branch_id") long branch_id
    		){
    	logger.info("Update account request");
    	logger.info("Validating update account request...");
    	if(!validator.isValidUpdate(id, branch_id)) {
    		logger.info("validation failed");
    		return null;
    	}
    	Branch branch = branchRep.findById(branch_id);
    	logger.info("Inserting account to database.");
    	Account account = accountCache.save(new Account(id,balance,branch), id);
    	if(account!= null) {
	    	logger.info("Account updated Successfuly: " + id);
	    	return account;
    	}
    	logger.info("An error occurred, account was not updated");
    	return null;
    }
    
    @RequestMapping(value = "/searchByBranchId", method = RequestMethod.POST)
    public List<Account> getAccountsByBranchId(
    		@RequestParam("branch_id") long branch_id){
    	logger.info("Searching account by branch id: "+ branch_id);
    	return accountRep.findAccountByBranchId(branch_id);
	}
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String deleteAccount(@RequestParam("id") long id) {
    	logger.info("Delete account request for account id: "+ id);
    	logger.warn("Deleting account and its refferenced children");
    	if(!validator.isExist(id)) {
    		logger.warn("No existing account for id: "+ id+ " delete operation failed");
    		return "No existing account for id: " + id;
    	}
        accountRep.deleteById(id);
        if(accountRep.existsById(id)) {
        	return "Error - Account Was not deleted";
        }
        return "Deleted Successfuly";
    }
    
    @RequestMapping(value = "/deleteByBranchId", method = RequestMethod.POST)
    public String deleteAccountByBranchId(@RequestParam("branch_id") long id) {
    	logger.info("Deleting accounts by branch id: "+ id);
    	logger.warn("This action will delete all accounts reffered to the branch id " + id + " and their refferenced children");
    	if(!validator.isParentExist(id)) {
    		logger.warn("No existing Branch for id: "+ id+ " delete operation failed");
    		return "No existing Branch for id: " + id;
    	}
    	accountRep.deleteAccountByBranchId(id);
        if(!accountRep.findAccountByBranchId(id).isEmpty()) {
        	return "Error - Accounts Were not deleted";
        }
        return "Deleted Successfuly";
    }
}

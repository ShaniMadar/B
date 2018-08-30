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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.madar.bankDemo.cacheInterface.BranchCacheInterface;
import com.madar.bankDemo.model.Bank;
import com.madar.bankDemo.model.Branch;
import com.madar.bankDemo.repository.BankRepository;
import com.madar.bankDemo.repository.BranchRepository;
import com.madar.bankDemo.validation.BranchValidation;

@RestController
@RequestMapping(value = "/branches")
public class BranchResource {
    @Autowired
    BranchCacheInterface branchCache;
    @Autowired
    BranchRepository branchRep;
    @Autowired
    BankRepository bankRep;
    @Autowired
    BranchValidation validator;
    

    private static final Logger logger = LoggerFactory.getLogger(BranchResource.class);
    
    @GetMapping(value = "/{id}")
    public Branch getBranch(@PathVariable final long id) {
    	logger.info("Retreveing Branch Data From Database by branch id:" + id);
        return branchCache.getBranch(id);
    }
    
    @GetMapping(value = "/all")
    public ArrayList<Branch> getAllBranches() {
    	logger.info("Retreveing Branches Data From Database");
        return (ArrayList<Branch>) branchRep.findAll();
    }
    
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Branch addNewBranch(
    		@RequestParam("id") long id,
    		@RequestParam("adress") String adress,
    		@RequestParam("bank_id") long bank_id
    		){
    	logger.info("Insert new branch request");
    	logger.info("Validating insert branch request...");
    	if(!validator.isValidInsert(id, bank_id)) {
    		logger.info("validation failed");
    		return null;
    	}
    	Bank bank = bankRep.findById(bank_id);
    	logger.info("Inserting branch to database.");
    	Branch branch = branchCache.save(new Branch(id,adress,bank), id);
    	if(branch!= null) {
	    	logger.info("Branch Created Successfuly: " + id);
	    	return branch;
    	}
    	logger.info("An error occurred, branch was not created");
    	return null;
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Branch updateBranch(
    		@RequestParam("id") long id,
    		@RequestParam("adress") String adress,
    		@RequestParam("bank_id") long bank_id
    		){
    	logger.info("Update branch request");
    	logger.info("Validating update branch request...");
    	if(!validator.isValidUpdate(id, bank_id)) {
    		logger.info("validation failed");
    		return null;
    	}
    	Bank bank = bankRep.findById(bank_id);
    	logger.info("Inserting branch to database.");
    	Branch branch = branchCache.save(new Branch(id,adress,bank), id);
    	if(branch!= null) {
	    	logger.info("Branch Created Successfuly: " + id);
	    	return branch;
    	}
    	logger.info("An error occurred, branch was not created");
    	return null;
    }
    
    @RequestMapping(value = "/searchByBankId", method = RequestMethod.POST)
    @ResponseBody
    public List<Branch> getBranchByBankId(
    		@RequestParam("bank_id") long bank_id){
    	logger.info("Searching branch by bank id: "+ bank_id);
    	return branchRep.findBranchByBankId(bank_id);
	}
    
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteBranch(@RequestParam("id") Long id) {
    	logger.info("Delete branch request for branch id: "+ id);
    	logger.warn("Deleting Branch and its refferenced children");
    	if(!validator.isExist(id)) {
    		logger.warn("No existing branch for id: "+ id+ " delete operation failed");
    		return "No existing branch for id: " + id;
    	}
        branchRep.deleteById(id);
        if(branchRep.existsById(id)) {
        	return "Error - Branch Was not deleted";
        }
        return "Deleted Successfuly";
    }
    
    @RequestMapping(value = "/deleteByBankId", method = RequestMethod.POST)
    @ResponseBody
    public String deleteBranchByBankId(@RequestParam("bank_id") Long id) {
    	logger.info("Deleting branches by bank id: "+ id);
    	logger.warn("This action will delete all branches reffered to the bank id " + id + " and their refferenced children");
    	if(!validator.isParentExist(id)) {
    		logger.warn("No existing bank for id: "+ id+ " delete operation failed");
    		return "No existing bank for id: " + id;
    	}
        branchRep.deleteBranchByBankId(id);
        if(!branchRep.findBranchByBankId(id).isEmpty()) {
        	return "Error - Branches Were not deleted";
        }
        return "Deleted Successfuly";
    }
}

package com.madar.bankDemo.resource;


import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.madar.bankDemo.cacheInterface.BankCacheInterface;
import com.madar.bankDemo.model.Bank;
import com.madar.bankDemo.repository.BankRepository;
import com.madar.bankDemo.repository.BranchRepository;

/**
 * @author madar
 * 
 */
@RestController
@RequestMapping(value = "/banks")
public class BankResource {
    @Autowired
    BankCacheInterface bankCache;
    @Autowired
    BankRepository bankRep;
    @Autowired
    BranchRepository branchRep;

    private static final Logger logger = LoggerFactory.getLogger(BankResource.class);
    
    @GetMapping(value = "/{id}")
    public Bank getBank(@PathVariable final long id) {
    	logger.info("Retreive bank data by id: "+ id);
        return bankCache.getBank(id);
    }
    
    @GetMapping(value = "/all")
    public ArrayList<Bank> getAllBanks() {
    	logger.info("Retreive banks data from database");
        return (ArrayList<Bank>) bankRep.findAll();
    }
    
    @PostMapping(value = "/add")
    public Bank addNewBank(@RequestParam("id") long id){
    	logger.info("Add new bank request.");
    	if(bankRep.existsById(id)) {
    		logger.warn("Bank Already Exists For this Id: " + id);
    		return null;
    	}
    	Bank bank = bankCache.save(id);
    	if(bank!= null) {
	    	logger.info("Bank Created Successfuly: " + id);
	    	return bank;
    	}
    	logger.warn("Something went wrong, bank was not created");
    	return null;
    }
    
    @PostMapping(value = "/delete")
    public String deleteBank(@RequestParam("id") long id) {
    	logger.info("Delete bank request.");
    	if(!branchRep.findBranchByBankId(id).isEmpty()) {
    		logger.warn("This bank has branches, therefor it was not deleted.");
    		return "This Bank has branches, please delete them first or use the 'deleteCascade' request'";
    	}
        bankRep.deleteById(id);
        if(bankRep.existsById(id)) {
        	logger.info("An error occurred, bank was not deleted.");
        	return "Error - Bank Was not deleted";
        }
        logger.info("Bank was deleted successfuly");
        return "Bank was deleted successfuly";
    }
    
    @PostMapping(value = "/deleteCascade")
    public String deleteBankCascade(@RequestParam("id") long id) {
    	logger.info("Delete bank request.");
        bankRep.deleteById(id);
        if(bankRep.existsById(id)) {
        	logger.info("An error occurred, bank was not deleted.");
        	return "Error - Bank Was not deleted";
        }
        logger.info("Bank was deleted successfuly");
        return "Bank, branches, accounts and customers deleted duccessfuly";
    }
    
    @PostMapping(value = "/update")
    public Bank updateBank(@RequestParam("id") long id) {
    	//Bank has no parameters to update.
    	logger.info("Update Bank Request.");
        if(!bankRep.existsById(id)) {
        	logger.info("Bank "+id+" doesn't exist in the database, failed to update bank...");
        	return null;
        }
        return bankRep.save(new Bank(id));
    }
    
    
}

package com.madar.bankDemo.resource;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.madar.bankDemo.cacheInterface.CustomerCacheInterface;
import com.madar.bankDemo.model.Account;
import com.madar.bankDemo.model.Customer;
import com.madar.bankDemo.repository.AccountRepository;
import com.madar.bankDemo.repository.CustomerRepository;
import com.madar.bankDemo.validation.CustomerValidation;

/**
 * @author madar
 *
 */
@RestController
@RequestMapping(value = "/customers")
public class CustomerResource {
	
    @Autowired
    CustomerCacheInterface customerCache;
    @Autowired
    CustomerRepository customerRep;
    @Autowired
    AccountRepository accountRep;
    
    /**
     * validates the existence of the relevant objects in the database according to the request.
     */
    @Autowired
    CustomerValidation validator;
    

    private static final Logger logger = LoggerFactory.getLogger(CustomerResource.class);
    
    @GetMapping(value = "/{id}")
    public Customer getAccount(@PathVariable final long id) {
    	logger.info("Retreveing Customer Data From Database by Customer id:" + id);
        return customerCache.getCustomer(id);
    }
    
    @GetMapping(value = "/all")
    public ArrayList<Customer> getAllCustomer() {
    	logger.info("Retreveing accounts Data From Database");
        return (ArrayList<Customer>) customerRep.findAll();
    }
    
    @PostMapping(value = "/add")
    public Customer addNewCustomer(
    		@RequestParam("id") long id,
    		@RequestParam("firstName") String firstName,
    		@RequestParam("lastName") String lastName,
    		@RequestParam("gender") String gender,
    		@RequestParam("account_id") long account_id
    		){
    	logger.info("Insert new Customer request");
    	logger.info("Validating insert Customer request...");
    	if(!validator.isValidInsert(id, account_id)) {
    		logger.info("validation failed");
    		return null;
    	}
    	Account account = accountRep.findById(account_id);
    	logger.info("Inserting Customer to database.");
    	Customer customer = customerCache.save(new Customer(id,firstName,lastName, gender, account), id);
    	if(customer!= null) {
	    	logger.info("Customer Created Successfuly: " + id);
	    	return customer;
    	}
    	logger.info("An error occurred, Customer was not created");
    	return null;
    }
    
    @PostMapping(value = "/update")
    public Customer updateCustomer(
    		@RequestParam("id") long id,
    		@RequestParam("firstName") String firstName,
    		@RequestParam("lastName") String lastName,
    		@RequestParam("gender") String gender,
    		@RequestParam("account_id") long account_id
    		){
    	logger.info("Update Customer request");
    	logger.info("Validating update customer request...");
    	if(!validator.isValidUpdate(id, account_id)) {
    		logger.info("validation failed");
    		return null;
    	}
    	Account account = accountRep.findById(account_id);
    	logger.info("Updating Customer in database.");
    	Customer customer = customerCache.save(new Customer(id,firstName,lastName, gender, account), id);
    	if(customer!= null) {
	    	logger.info("Customer updated Successfuly: " + id);
	    	return customer;
    	}
    	logger.info("An error occurred, Customer was not updated");
    	return null;
    }
    
    @PostMapping(value = "/searchByAccountId")
    public List<Customer> getCustomerByAccountId(
    		@RequestParam("account_id") long account_id){
    	logger.info("Searching Customer by account id: "+ account_id);
    	return customerRep.findCustomersByAccountId(account_id);
	}
    
    @PostMapping(value = "/delete")
    public String deleteCustomer(@RequestParam("id") long id) {
    	logger.info("Delete Customer request for Customer id: "+ id);
    	logger.warn("Deleting Customer and its refferenced children");
    	if(!validator.isExist(id)) {
    		logger.warn("No existing customer for id: "+ id+ " delete operation failed");
    		return "No existing customer for id: " + id;
    	}
    	customerCache.delete(id);
        if(customerRep.existsById(id)) {
        	return "Error - Customer Was not deleted";
        }
        return "Deleted Successfuly";
    }
    
    @PostMapping(value = "/deleteByAccountId")
    public String deleteCustomerByAccountId(@RequestParam("account_id") Long id) {
    	logger.info("Deleting Customers by account id: "+ id);
    	logger.warn("This action will delete all Customers reffered to the account id " + id + " and their refferenced children");
    	if(!validator.isParentExist(id)) {
    		logger.warn("No existing Account for id: "+ id+ " delete operation failed");
    		return "No existing Account for id: " + id;
    	}
    	customerRep.deleteCustomersByAccountId(id);
        if(!customerRep.findCustomersByAccountId(id).isEmpty()) {
        	return "Error - Customers Were not deleted";
        }
        return "Deleted Successfuly";
    }
}

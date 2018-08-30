package com.madar.bankDemo.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.madar.bankDemo.cacheInterface.AccountCacheInterface;
import com.madar.bankDemo.cacheInterface.CustomerCacheInterface;
import com.madar.bankDemo.model.Customer;
import com.madar.bankDemo.repository.CustomerRepository;

@Component
public class CustomerCache implements CustomerCacheInterface{
    @Autowired
    CustomerRepository customerRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(CustomerCache.class);

    @Cacheable(value = "customerCache", key = "#id")
    public Customer getCustomer(long id) {
    	logger.info("Retrieving customer Data from Database customer Id: " + id);
        Customer customer = new Customer();
        customer = customerRepository.findById(id);
        return customer;
    }
	@CachePut(value = "customerCache", key = "#id")
    public Customer save(Customer customer, long id) {
		logger.info("Inserting Customer Data To Database customer Id: " + customer.getId());

        return customerRepository.save(customer);
    }
	@Override
	@CacheEvict(value = "customerCache", key = "#id")
	public void delete(long id) {
		customerRepository.deleteById(id);
		logger.info("Remove account Data from Cache memory: " + id);
	}
}

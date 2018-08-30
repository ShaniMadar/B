package com.madar.bankDemo.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.madar.bankDemo.cacheInterface.CustomerCacheInterface;
import com.madar.bankDemo.model.Customer;
import com.madar.bankDemo.repository.CustomerRepository;

@Component
public class CustomerCache implements CustomerCacheInterface{
    @Autowired
    CustomerRepository customerRepository;

    @Cacheable(value = "customerCache", key = "#id")
    public Customer getCustomer(long id) {
        System.out.println("Retrieving customer Data from Database customer Id: " + id);
        Customer customer = new Customer();
        customer = customerRepository.findById(id);
        return customer;
    }
	@CachePut(value = "customerCache", key = "#id")
    public Customer save(Customer customer, long id) {
        System.out.println("Inserting Customer Data To Database customer Id: " + customer.getId());

        return customerRepository.save(customer);
    }
}

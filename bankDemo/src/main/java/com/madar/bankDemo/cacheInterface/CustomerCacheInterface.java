package com.madar.bankDemo.cacheInterface;

import org.springframework.stereotype.Service;

import com.madar.bankDemo.model.Customer;

@Service
public interface CustomerCacheInterface {
	Customer getCustomer(long id);
	Customer save(Customer customer, long id);
	void delete(long id);
}

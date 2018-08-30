package com.madar.bankDemo.cacheInterface;

import org.springframework.stereotype.Service;

import com.madar.bankDemo.model.Bank;

@Service
public interface BankCacheInterface{
	
    Bank getBank(long id);
    Bank save(long id);
    
}

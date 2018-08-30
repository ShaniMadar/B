package com.madar.bankDemo.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.madar.bankDemo.cacheInterface.BankCacheInterface;
import com.madar.bankDemo.model.Bank;
import com.madar.bankDemo.repository.BankRepository;

@Component
public class BankCache implements BankCacheInterface{
    @Autowired
    BankRepository bankRepository;

    @Cacheable(value = "bankCache", key = "#id")
    public Bank getBank(long id) {
        System.out.println("Retrieving Bank Data from Database Bank Id: " + id);
        Bank bank = new Bank();
        bank = bankRepository.findById(id);
        return bank;
    }
	@CachePut(value = "bankCache", key = "#id")
    public Bank save(long id) {
        System.out.println("Inserting Bank Data To Database Bank Id: " + id);

        return bankRepository.save(new Bank(id));
    }
}

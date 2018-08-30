package com.madar.bankDemo.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.madar.bankDemo.cacheInterface.AccountCacheInterface;
import com.madar.bankDemo.model.Account;
import com.madar.bankDemo.repository.AccountRepository;

/**
 * @author madar
 *
 *Caching the @Account entity data, saves and retrieves the data by id number.
 *
 */
@Component
public class AccountCache implements AccountCacheInterface{
    @Autowired
    AccountRepository accountRepository;

	@Override
	@Cacheable(value = "accountCache", key = "#id")
	public Account getAccount(long id) {
        System.out.println("Retrieving account Data from Database account Id: " + id);
        Account account = new Account();
        account = accountRepository.findById(id);
        return account;
	}

	@Override
	@CachePut(value = "accountCache", key = "#id")
	public Account save(Account account, long id) {
        System.out.println("Inserting account Data To Database account Id: " + account.getId());

        return accountRepository.save(account);
	}
    
    
}

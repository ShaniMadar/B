package com.madar.bankDemo.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.madar.bankDemo.cacheInterface.AccountCacheInterface;
import com.madar.bankDemo.model.Account;
import com.madar.bankDemo.repository.AccountRepository;
import com.madar.bankDemo.resource.AccountResource;

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
    
    private static final Logger logger = LoggerFactory.getLogger(AccountCache.class);

	@Override
	@Cacheable(value = "accountCache", key = "#id")
	public Account getAccount(long id) {
        logger.info("Inserting data to Cache account id: " + id);
        Account account = new Account();
        account = accountRepository.findById(id);
        return account;
	}

	@Override
	@CachePut(value = "accountCache", key = "#id")
	public Account save(Account account, long id) {
		logger.info("Inserting account Data To Cache account Id: " + account.getId());

        return accountRepository.save(account);
	}

	@Override
	@CacheEvict(value = "accountCache", key = "#id")
	public void delete(long id) {
		accountRepository.deleteById(id);
		logger.info("Remove account Data from Cache memory: " + id);
        return;
	}    
}

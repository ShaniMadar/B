package com.madar.bankDemo.cacheInterface;

import org.springframework.stereotype.Service;

import com.madar.bankDemo.model.Account;

@Service
public interface AccountCacheInterface {
	Account getAccount(long id);
	Account save(Account account, long id);
}

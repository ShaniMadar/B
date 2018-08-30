package com.madar.bankDemo.cacheInterface;

import org.springframework.stereotype.Service;

import com.madar.bankDemo.model.Branch;

@Service
public interface BranchCacheInterface {
	Branch getBranch(long id);
	Branch save(Branch branch, long id);
}

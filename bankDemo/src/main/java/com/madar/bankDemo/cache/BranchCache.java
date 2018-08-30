package com.madar.bankDemo.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.madar.bankDemo.cacheInterface.AccountCacheInterface;
import com.madar.bankDemo.cacheInterface.BranchCacheInterface;
import com.madar.bankDemo.model.Branch;
import com.madar.bankDemo.repository.BranchRepository;

@Component
public class BranchCache implements BranchCacheInterface{
    @Autowired
    BranchRepository branchRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(BranchCache.class);

    @Cacheable(value = "branchCache", key = "#id")
    public Branch getBranch(long id) {
    	logger.info("Inserting Branch Data to cache: " + id);
        Branch branch = new Branch();
        branch = branchRepository.findById(id);
        return branch;
    }
    
	@CachePut(value = "branchCache", key = "#id")
    public Branch save(Branch branch, long id) {
		logger.info("update data in cache: " + branch.getId());
        Branch b =branchRepository.save(branch);
        return b;
    }
	
	@Override
	@CacheEvict(value = "branchCache", key = "#id")
	public void delete(long id) {
		branchRepository.deleteById(id);
		logger.info("Remove branch Data from Cache memory: " + id);
        return;
	}
}

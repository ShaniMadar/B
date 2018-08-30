package com.madar.bankDemo.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import com.madar.bankDemo.cacheInterface.BranchCacheInterface;
import com.madar.bankDemo.model.Branch;
import com.madar.bankDemo.repository.BranchRepository;

@Component
public class BranchCache implements BranchCacheInterface{
    @Autowired
    BranchRepository branchRepository;

    @Cacheable(value = "branchCache", key = "#id")
    public Branch getBranch(long id) {
        System.out.println("NOT IN CACHE Retrieving Branch Data from Database Branch Id: " + id);
        Branch branch = new Branch();
        branch = branchRepository.findById(id);
        return branch;
    }
	@CachePut(value = "branchCache", key = "#id")
    public Branch save(Branch branch, long id) {
        System.out.println("NOT IN CACHE Inserting Branch Data To Database Branch Id: " + branch.getId());
        Branch b =branchRepository.save(branch);
        return b;
    }
}

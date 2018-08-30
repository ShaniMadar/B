package com.madar.bankDemo.loader;

import com.madar.bankDemo.model.Account;
import com.madar.bankDemo.model.Bank;
import com.madar.bankDemo.model.Branch;
import com.madar.bankDemo.model.Customer;
import com.madar.bankDemo.repository.AccountRepository;
import com.madar.bankDemo.repository.BankRepository;
import com.madar.bankDemo.repository.BranchRepository;
import com.madar.bankDemo.repository.CustomerRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author madar
 * 
 * The Loader class loads hard-coded data to the database in @PostConstruct .
 *
 */
@Component
public class Loader {

    @Autowired
    BankRepository bankRepository;
    @Autowired
    BranchRepository branchRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;

    private List<Bank> bankList;
    private List<Branch> branchList;
    private List<Account> accountList;
    private List<Customer> customerList;
    
    @PostConstruct
    public void load() {
        bankList = getBankList();
        bankList = bankRepository.saveAll(bankList);
        branchList = getBranchList();
        branchList = branchRepository.saveAll(branchList);
        accountList = getAccountList();
        accountList = accountRepository.saveAll(accountList);
        customerList = getCustomerList();
        customerList = customerRepository.saveAll(customerList);
    }

    public List<Bank> getBankList() {
        List<Bank> bankList = new ArrayList<>();
        bankList.add(new Bank(123300L));
        bankList.add(new Bank(456300L));
        bankList.add(new Bank(125443L));
        bankList.add(new Bank(453336L));
        bankList.add(new Bank(122223L));
        bankList.add(new Bank(451136L));
        return bankList;
    }
    
    public List<Branch> getBranchList() {
        List<Branch> branchList = new ArrayList<>();
        branchList.add(new Branch(1223L, "Hemilton st. 1233", bankRepository.findById(123300L)));
        branchList.add(new Branch(1122L, "Borochov TLV", bankRepository.findById(123300L)));
        branchList.add(new Branch(2244L, "Jakarta Indonesia", bankRepository.findById(123300L)));
        branchList.add(new Branch(59648L, "4455 Silicon Valley", bankRepository.findById(456300L)));
        branchList.add(new Branch(85697L, "1334 Bertrxhzin Berlin G", bankRepository.findById(456300L)));
        branchList.add(new Branch(44887L, "346 Plaza building", bankRepository.findById(456300L)));
        branchList.add(new Branch(1223L, "Hemilton st. 1233", bankRepository.findById(125443L)));
        branchList.add(new Branch(1122L, "Borochov TLV", bankRepository.findById(125443L)));
        return branchList;
    }
    
    public List<Account> getAccountList() {
        List<Account> accountList = new ArrayList<>();
        float min = 1000F;
        float max = 100000F;
        long id = 111;
        for(Branch b : branchList) {
        	accountList.add(new Account(id++,(float)Math.round((float) min + new Random().nextFloat() * (max - min)*100)/100, b));
            accountList.add(new Account(id++,(float)Math.round((float) min + new Random().nextFloat() * (max - min)*100)/100, b));
            accountList.add(new Account(id++,(float)Math.round((float) min + new Random().nextFloat() * (max - min)*100)/100, b));
        }
        return accountList;
    }
    
    public List<Customer> getCustomerList() {
        List<Customer> customerList = new ArrayList<>();
        long id = 111;
        for(int i = 0; i< accountList.size(); i++ ) {
        	Account a = accountList.get(i);
        	int num = 1 + (int) (new Random().nextFloat() * (4 - 1));
        	for(int j = num; j>0; j--) {
        		customerList.add(new Customer(id++,"abc", "def", "male", a));
        	}
        }
        return customerList;
    }
	
}

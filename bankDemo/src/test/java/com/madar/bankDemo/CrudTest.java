package com.madar.bankDemo;

import com.madar.bankDemo.model.Account;
import com.madar.bankDemo.model.Bank;
import com.madar.bankDemo.model.Branch;
import com.madar.bankDemo.model.Customer;
import com.madar.bankDemo.repository.AccountRepository;
import com.madar.bankDemo.repository.BankRepository;
import com.madar.bankDemo.repository.BranchRepository;
import com.madar.bankDemo.repository.CustomerRepository;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CrudTest {

    @Autowired
    BankRepository bankRepository;
    @Autowired
    BranchRepository branchRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    CustomerRepository customerRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(CrudTest.class);
    
    private static boolean firstLoad = true;
    private static List<Bank> banks;
    private static List<Branch> branches;
    private static List<Account> accounts;
    private static List<Customer> customers;
    
    private static final int indexForSelectTesting = 0;
    private static final int indexForUpdateTesting = 1;
    private static final int indexForDeleteTesting = 2;
    private static final int ParameterindexForUpdateTesting = 5;

    @Before
    public void createLists() {
    	if(firstLoad) {
    		logger.info("Creating lists for testing...");
    	banks = getBankList();
    	branches = getBranchList();
    	accounts = getAccountList();
    	customers = getCustomerList();	
    	}
       	insertToDBForTesting();
    }
    
    public void insertToDBForTesting() {
    	logger.info("Saving lists in database and assigning the returned values to the lists");
    	banks = bankRepository.saveAll(banks);
    	branches = branchRepository.saveAll(branches);
    	accounts = accountRepository.saveAll(accounts);
    	customers = customerRepository.saveAll(customers);
    }
    
    @Test
    public void testBankInsert() {
    	logger.info("Testing Insert Bank To Database");
    	
    	Bank bank = new Bank(9093290L);
    	Bank bankTest = bankRepository.save(bank);
    	
    	Assert.assertEquals(false, bankTest == null);
    }
    
    @Test
    public void testBranchInsert() {	
    	logger.info("Testing Insert Branch To Database");
    	
    	Branch branch = new Branch(112211L, "ABC adress",banks.get(ParameterindexForUpdateTesting)); 	
    	Branch branchTest = branchRepository.save(branch);
    	
    	Assert.assertEquals(false, branchTest == null);
    }
    
	@Test
    public void testAccountInsert() {
		logger.info("Testing Insert Account To Database");

		Account account = new Account(901920, 333444.34F, branches.get(ParameterindexForUpdateTesting));
		Account accountTest = accountRepository.save(account);
		
		Assert.assertEquals(false, accountTest == null);
	}
	
	@Test
    public void testCustomerInsert() {	
		logger.info("Testing Insert Customer To Database");

		Customer customer = new Customer(44773388L, "Jhon", "Doe", "Female", accounts.get(ParameterindexForUpdateTesting));
		Customer customerTest = customerRepository.save(customer);
		
		Assert.assertEquals(false, customerTest == null);
    }
      
    @Test
    public void testBankSelect() {
    	logger.info("Testing Select statement for Bank database");
    	
    	long bankId = banks.get(indexForSelectTesting).getId();
    	Bank b = bankRepository.findById(bankId);
    	
    	Assert.assertEquals(true, b!=null);
    }
    
    @Test
    public void testBankUpdate() {
    	//Bank has no parameters to update at the moment.
    	logger.info("Testing Update statement for Bank database");
    }
    
    @Test
    public void testBankDelete() {
    	logger.info("Testing Delete statement for Bank database");
    	
    	long bankId = banks.get(indexForDeleteTesting).getId();
    	bankRepository.deleteById(bankId);
    	Bank b = bankRepository.findById(bankId);
    	
    	Assert.assertEquals(true, b==null);
    }
    
    @Test
    public void testBranchSelect() {
    	logger.info("Testing Select statement for Branch database");
    	
    	long branchId = branches.get(indexForSelectTesting).getId();
    	Branch b = branchRepository.findById(branchId);
    	
    	Assert.assertEquals(true, b!=null);
    }
    
    @Test
    public void testBranchUpdate() {
    	logger.info("Testing Update statement for Branch database");
    	 	
    	long branchId = branches.get(indexForUpdateTesting).getId();
    	Branch branch = branchRepository.findById(branchId);
    	branch.setAdress("This is NEW adress");
    	branchRepository.save(branch);
    	
    	Assert.assertTrue(branch.getAdress().equals(branchRepository.findById(branchId).getAdress()));
    }
    
    @Test
    public void testBranchDelete() {
    	logger.info("Testing Delete statement for Branch database");
    	
    	long branchId = branches.get(indexForDeleteTesting).getId();
    	Branch branch = branchRepository.findById(branchId);
    	branchRepository.deleteById(branchId);
    	branch = branchRepository.findById(branchId);

    	Assert.assertEquals(true, branch == null);
    }

    @Test
    public void testAccountSelect() {
    	logger.info("Testing Select statement for Branch database");
    	
    	long accountId = accounts.get(indexForSelectTesting).getId();
    	Account a = accountRepository.findById(accountId);
    	
    	Assert.assertEquals(true, a!=null);
    }
    
    @Test
    public void testAccountUpdate() {
    	logger.info("Testing Update statement for Branch database");

    	long accountId = accounts.get(indexForUpdateTesting).getId();
    	Account account = accountRepository.findById(accountId);
    	account.setBranch(branches.get(ParameterindexForUpdateTesting));
    	account.setBalance(-22222.33F);
    	accountRepository.save(account);
    	
    	Account accountTest= accountRepository.findById(accountId);
    	
    	long branch = branches.get(ParameterindexForUpdateTesting).getId();
    	long branchTest = account.getBranch().getId();
    	
    	Assert.assertTrue(branchTest == branch);
    	Assert.assertTrue(accountTest.getBalance() == account.getBalance());
    }
    
    @Test
    public void testAccountDelete() {
    	logger.info("Testing Delete statement for Branch database");
    	
    	long accountId = accounts.get(indexForDeleteTesting).getId();
    	accountRepository.deleteById(accountId);
    	Account account = accountRepository.findById(accountId);
    	
    	Assert.assertEquals(true, account == null);
    }
    
    @Test
    public void testCustomerSelect() {
    	logger.info("Testing Select statement for Customer database");
    	
    	long customerId = customers.get(indexForSelectTesting).getId();
    	Customer customer = customerRepository.findById(customerId);
    	
    	Assert.assertEquals(true, customer!=null);
    }
    
    @Test
    public void testCustomerUpdate() {
    	logger.info("Testing Update statement for Customer database");

    	long customerId = customers.get(indexForUpdateTesting).getId();
    	Customer customer = customerRepository.findById(customerId);
    	customer.setAccount(accounts.get(ParameterindexForUpdateTesting));
    	customer.setFirstName("Jhon");
    	customerRepository.save(customer);
    	
    	Customer customerTest = customerRepository.findById(customerId);
    	long accountId = accounts.get(ParameterindexForUpdateTesting).getId();
    	long accountIdTest = customerTest.getAccount().getId();
    	
    	Assert.assertTrue(accountId == accountIdTest);
    	Assert.assertTrue(customerTest.getFirstName().equals(customer.getFirstName()));
    }
    
    @Test
    public void testCustomerDelete() {
    	logger.info("Testing Delete statement for Customer database");
    	
    	long accountId = accounts.get(indexForDeleteTesting).getId();
    	accountRepository.deleteById(accountId);
    	Account account = accountRepository.findById(accountId);
    	
    	Assert.assertEquals(true, account == null);
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
        for(Branch b : branches) {
        	accountList.add(new Account(id++,(float)Math.round((float) min + new Random().nextFloat() * (max - min)*100)/100, b));
            accountList.add(new Account(id++,(float)Math.round((float) min + new Random().nextFloat() * (max - min)*100)/100, b));
            accountList.add(new Account(id++,(float)Math.round((float) min + new Random().nextFloat() * (max - min)*100)/100, b));
        }
        return accountList;
    }
    
    public List<Customer> getCustomerList() {
        List<Customer> customerList = new ArrayList<>();
        long id = 111;
        for(int i = 0; i< accounts.size(); i++ ) {
        	Account a = accounts.get(i);
        	int num = 1 + (int) (new Random().nextFloat() * (4 - 1));
        	for(int j = num; j>0; j--) {
        		customerList.add(new Customer(id++,"abc", "def", "male", a));
        	}
        }
        return customerList;
    }
	
}

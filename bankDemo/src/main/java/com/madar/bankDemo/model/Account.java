package com.madar.bankDemo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Account implements Serializable{
    @Id
    private Long id;
    private Float balance;
    
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    
    @OneToMany(mappedBy = "account", orphanRemoval = true, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Customer> customers = new ArrayList<Customer>();
    
    public Account() {
    	
    }
    public Account(Long id) {
		this.id = id;
	}
    
    public Account(Float balance, Branch branch) {
    	this.setBalance(balance);
    	this.setBranch(branch);
    }
    
    public Account(long id, Float balance, Branch branch) {
    	this.setId(id);
    	this.setBalance(balance);
    	this.setBranch(branch);
    }
	public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public Branch getBranch() {
		return branch;
	} 
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	public String toString() {
		return "Account Id: "+this.id+
				" Branch: " + this.branch.getId()+
				" Balance: " + this.balance;
	}
}

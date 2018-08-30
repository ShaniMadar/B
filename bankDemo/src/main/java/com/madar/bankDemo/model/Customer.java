package com.madar.bankDemo.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Customer implements Serializable{
    @Id
    private long id;
    private String firstname;
    private String lastname;
    private String gender;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
    
    public Customer() {
    	
    }
    public Customer(long id, String firstName, String lastName, String gender, Account account) {
    	this.id = id;
    	this.firstname = firstName;
    	this.lastname = lastName;
    	this.gender = gender;
    	this.account = account;
    }
    public Customer(String firstName, String lastName, String gender, Account account) {
    	this.firstname = firstName;
    	this.lastname = lastName;
    	this.gender = gender;
    	this.account = account;
    }
    public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
	public String getFirstName() {
		return firstname;
	}
	public void setFirstName(String firstName) {
		this.firstname = firstName;
	}
	public String getLastName() {
		return lastname;
	}
	public void setLastName(String lastName) {
		this.lastname = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Account getAccount() {
		return account;
	} 
	public void setAccount(Account account) {
		this.account = account;
	}
	public String toString() {
		return "Customer Id: "+this.id+
		" First Name: "+this.firstname +
		" Last Name: "+this.lastname +
		" Gender: "+this.gender+
		" Account: "+this.account.getId()+
		" Balance: "+this.account.getBalance()+"";
	}
}

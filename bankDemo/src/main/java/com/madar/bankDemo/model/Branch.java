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
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "BRANCH")
public class Branch implements Serializable{
    @Id
    private long id;
    private String adress;
    
    @ManyToOne
    @JoinColumn(name = "bank_id")
    private Bank bank;
    
    @OneToMany(mappedBy = "branch", orphanRemoval = true, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Account> accounts = new ArrayList<Account>();
    
    public Branch() {
    	
    }
    public Branch(long id, String adress, Bank bank) {
    	this.id = id;
    	this.setAdress(adress);
    	this.setBank(bank);
    }
    public Branch(long id) {
		this.id = id;
	}
	public Long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
	public String getAdress() {
		return adress;
	}
	public void setAdress(String adress) {
		this.adress = adress;
	}
	public Bank getBank() {
		return bank;
	} 
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	public String toString() {
		return "Branch Id: "+ this.id+
		" Bank: "+ this.bank.getId()+
		" Adress: "+ this.adress;
	}
}

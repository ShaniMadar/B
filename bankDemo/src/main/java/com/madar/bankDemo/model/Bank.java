package com.madar.bankDemo.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
public class Bank implements Serializable{
    @Id
    private Long id;
    @OneToMany(mappedBy = "bank", orphanRemoval = true, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Branch> branches = new ArrayList<Branch>();
    
    public Bank() {
    	
    }
    public Bank(long id) {
    	this.id = id;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String toString() {
    	return "Bank Id: "+this.id;
    }

}

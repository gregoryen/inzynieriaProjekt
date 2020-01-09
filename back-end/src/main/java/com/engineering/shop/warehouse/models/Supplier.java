package com.engineering.shop.warehouse.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplierId;

    @NotNull
    private String firstname;

    @NotNull
    private String lastname;

    @NotNull
    private String company;

    @NotNull
    private String phoneNumber;

    public Supplier() {
    }

    public Supplier(Integer supplierId, String firstname, String lastname, String company, String phoneNumber) {
        this.supplierId = supplierId;
        this.firstname = firstname;
        this.lastname = lastname;
        this.company = company;
        this.phoneNumber = phoneNumber;
    }

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
    
    
}

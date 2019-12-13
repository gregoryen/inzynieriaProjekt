package com.engineering.shop.warehouse.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    public Supplier(String firstname, String lastname, String company, String phoneNumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.company = company;
        this.phoneNumber = phoneNumber;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setId(Integer supplierId) {
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

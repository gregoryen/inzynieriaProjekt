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
}

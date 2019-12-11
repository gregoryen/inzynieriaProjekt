package com.engineering.shop.products;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
@RequiredArgsConstructor
public class Product {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    private String name;
    private String description;
    private Integer categoryId;
    private BigDecimal price;
    private String reference;
    private String isbn;
    private String ean13;
    private boolean active;


}

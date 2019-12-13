package com.engineering.shop.cart.product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String name;
    private float productPrice;

    public Product() {
    }

    public Product(String name, float productInt) {
        this.name = name;
        this.productPrice = productInt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductInt(int productPrice) {
        this.productPrice = productPrice;
    }
}

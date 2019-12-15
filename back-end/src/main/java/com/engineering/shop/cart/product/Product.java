package com.engineering.shop.cart.product;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
// klasa testowa
import javax.persistence.*;

@Entity
@Table(name="Product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String name;
    private float productPrice;

    public Product() {
    }

    public Product(String name, float productPrice) {
        this.name = name;
        this.productPrice = productPrice;
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

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }
}

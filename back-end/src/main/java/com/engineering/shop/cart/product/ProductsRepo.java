package com.engineering.shop.cart.product;


// KLASA ROBOCZA

import com.engineering.shop.cart.product.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductsRepo {

    private List<Product> magazine;

    public ProductsRepo() {
        this.magazine = new ArrayList<Product>();
    }

    public void addProduct(String name, Integer id) {
        magazine.add(new Product(name, id));
    }
}

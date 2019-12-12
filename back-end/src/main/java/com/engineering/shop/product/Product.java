package com.engineering.shop.product;

public class Product {
    private String name;
    private int productInt;

    public Product(String name, int productInt) {
        this.name = name;
        this.productInt = productInt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProductInt() {
        return productInt;
    }

    public void setProductInt(int productInt) {
        this.productInt = productInt;
    }
}

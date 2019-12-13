package com.engineering.shop.cart.bucketlist;

import com.engineering.shop.cart.product.Product;
import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class BucketPosition {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer bucketPositionId;

    @OneToOne
    @NotNull
    private Product product;
    //private Integer product;

    @NotNull
    private Integer productQuantity;

    public BucketPosition() {
    }



    public BucketPosition(Product product, int amount) {
        this.product = product;
        this.productQuantity = amount;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Integer getBucketPositionId() {
        return bucketPositionId;
    }

    public Product getProduct() {
        return product;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }
}

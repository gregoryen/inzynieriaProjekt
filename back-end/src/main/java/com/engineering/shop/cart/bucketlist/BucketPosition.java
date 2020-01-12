package com.engineering.shop.cart.bucketlist;

import com.engineering.shop.products.Product;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name="BucketPosition")
public class BucketPosition extends RepresentationModel<BucketPosition> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @NotNull
    @ManyToOne
    private Product product;

    private String bucketIndex;
    @NotNull
    private int productQuantity;
    @NotNull
    private BigDecimal productPrice;

    public BucketPosition(){

    }

    public BucketPosition( Product product, int productQuantity, BigDecimal price) {

        this.product = product;
        this.productQuantity = productQuantity;
        this.productPrice = price;

    }
/*
    public void setBucketIndex(String bucketIndex) {
        this.bucketIndex = bucketIndex;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Integer getId() {
        return id;
    }

    public Integer getProductId() {
        return productId;
    }

    public String getBucketIndex() {
        return bucketIndex;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    } */

    public void increaseProductQuantity(){
        this.productQuantity+=1;
    }

    public void decreaseProductQuantity(){
        if (this.productQuantity > 0 ) {
            this.productQuantity-=1;
        }
    }
}

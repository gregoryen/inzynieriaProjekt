package com.engineering.shop.cart.bucketlist;

import com.engineering.shop.cart.product.Product;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name="BucketPosition")
public class BucketPosition extends RepresentationModel<BucketPosition> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;
    @NotNull
    private Integer productId;
    private String bucketIndex;
    @NotNull
    private int productQuantity;
    @NotNull
    private float productPrice;

    public BucketPosition(){

    }

    public BucketPosition( Integer productId, int productQuantity, Float price) {

        this.productId = productId;
        this.productQuantity = productQuantity;
        this.productPrice = price;

    }

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

    public void increaseProductQuantity(){
        this.productQuantity+=1;
    }

    public void decreaseProductQuantity(){
        if (this.productQuantity > 0 ) {
            this.productQuantity-=1;
        }
    }

    public float getProductPrice() {
        return productPrice;
    }
}

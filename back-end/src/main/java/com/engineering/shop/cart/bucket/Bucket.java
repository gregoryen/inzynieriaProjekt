package com.engineering.shop.cart.bucket;


import com.engineering.shop.cart.bucketlist.BucketPosition;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Getter
//@Setter
@EnableAutoConfiguration
@Table(name="Bucket")
//@RequiredArgsConstructor
public class Bucket {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer bucketId;
    @NotNull
    private float totalValue;
    @NotNull
    private Integer orderId;

    public Bucket() {
    }

    public float getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(float totalValue) {
        this.totalValue = totalValue;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getBucketId(){
        return this.bucketId;
    }

}

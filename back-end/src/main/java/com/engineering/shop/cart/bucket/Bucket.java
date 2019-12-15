package com.engineering.shop.cart.bucket;


import com.engineering.shop.cart.bucketlist.BucketPosition;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="Bucket")
public class Bucket {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer bucketId;
    @NonNull
    private String bucketIndex;
    @NotNull
    private float totalValue;
    @NotNull
    private Integer orderId;

    public Bucket() {
        this.bucketIndex = UUID.randomUUID().toString();
        this.totalValue = 0.00f;
    }

    public float getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(float totalValue) {
        this.totalValue = totalValue;
    }

    public void increaseTotalValue(float value){
        this.totalValue += value;
    }

    public void decreaseTotalValue(float value){
        if (totalValue > 0){
            this.totalValue -= value;
        }
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

    public String getBucketIndex() {
        return bucketIndex;
    }
}

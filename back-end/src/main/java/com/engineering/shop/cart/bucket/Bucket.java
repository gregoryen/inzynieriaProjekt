package com.engineering.shop.cart.bucket;


import com.engineering.shop.cart.bucketlist.BucketPosition;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
//@Data
//@RequiredArgsConstructor
public class Bucket {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer bucketId;

    @OneToMany
    //@NotNull
    private List<BucketPosition> bucketPositions;
    private double totalValue;
    private Integer totalQuantity;

    public Bucket() {
    }

    public Bucket(BucketPosition bucketPosition) {
        this.bucketPositions = new ArrayList<>();
        this.bucketPositions.add(bucketPosition);
        this.totalValue = bucketPosition.getProduct().getProductPrice();
        this.totalQuantity = bucketPosition.getProductQuantity();
    }

    public Integer getBucketId() {
        return bucketId;
    }

    public void setBucketId(Integer bucketId) {
        this.bucketId = bucketId;
    }

    public List<BucketPosition> getBucketPositions() {
        return bucketPositions;
    }

    public void setBucketPositions(List<BucketPosition> bucketPositions) {
        this.bucketPositions = bucketPositions;
    }

    public double getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(double totalValue) {
        this.totalValue = totalValue;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}

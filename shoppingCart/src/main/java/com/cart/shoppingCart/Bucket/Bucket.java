package com.cart.shoppingCart.Bucket;

public class Bucket {

    private int bucketId;
    private int[] bucketPositionListById;
    private float totalValue;
    private int totalQuantity;

    public Bucket(int bucketId, int[] bucketPositionListById, float totalValue, int totalQuantity) {
        this.bucketId = bucketId;
        this.bucketPositionListById = bucketPositionListById;
        this.totalValue = totalValue;
        this.totalQuantity = totalQuantity;
    }

    public int getBucketId() {
        return bucketId;
    }

    public void setBucketId(int bucketId) {
        this.bucketId = bucketId;
    }

    public int[] getBucketPositionListById() {
        return bucketPositionListById;
    }

    public void setBucketPositionListById(int[] bucketPositionListById) {
        this.bucketPositionListById = bucketPositionListById;
    }

    public float getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(float totalValue) {
        this.totalValue = totalValue;
    }

    public int getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(int totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}

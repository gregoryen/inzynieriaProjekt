package com.cart.shoppingCart.BucktPosition;

import com.cart.shoppingCart.Product.Product;

public class BucketPosition {

    private int bucketPositionId;
    // trzba bedzie to zmienic z modulu Produkty
    private Product product;
    private int productQuantity;

    public BucketPosition(int bucketPositionId, Product product, int productQuantity) {
        this.bucketPositionId = bucketPositionId;
        this.product = product;
        this.productQuantity = productQuantity;
    }

    public int getBucketPositionId() {
        return bucketPositionId;
    }

    public void setBucketPositionId(int bucketPositionId) {
        this.bucketPositionId = bucketPositionId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }
}

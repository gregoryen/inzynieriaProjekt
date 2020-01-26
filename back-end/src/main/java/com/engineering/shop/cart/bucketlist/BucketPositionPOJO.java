package com.engineering.shop.cart.bucketlist;


import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class BucketPositionPOJO {

    //private Integer id;
    private Integer product;
    //private String productName;
    //private BigDecimal productPrice;
    private Integer productQuantity;
    private String bucket;

}

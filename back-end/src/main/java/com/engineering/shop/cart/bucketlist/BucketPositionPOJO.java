package com.engineering.shop.cart.bucketlist;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class BucketPositionPOJO {

    private Integer id;
    private Integer product;
    private String productName;
    private BigDecimal productPrice;
    private Integer productQuantity;

}

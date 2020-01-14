package com.engineering.shop.cart.order;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@RequiredArgsConstructor
public class OrderPOJO {

    private String firstName;
    private String lastName;
    private String adress;
    private String phoneNumber;
    private String email;
    private String orderBucketId;
    private BigDecimal orderValue;
    private Boolean isPaid;
}

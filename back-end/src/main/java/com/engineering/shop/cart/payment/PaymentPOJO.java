package com.engineering.shop.cart.payment;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class PaymentPOJO {

    Integer orderId;
    String paymentType;

}

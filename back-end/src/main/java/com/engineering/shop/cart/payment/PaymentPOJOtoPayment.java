package com.engineering.shop.cart.payment;

import com.engineering.shop.common.Transformer;
import org.springframework.stereotype.Service;

@Service
public class PaymentPOJOtoPayment implements Transformer<PaymentPOJO, Payment> {

    @Override
    public Payment transform(PaymentPOJO object) {
        return Payment.builder()
                .orderId(object.orderId)
                .paymentType(object.paymentType)
                .build();
    }
}

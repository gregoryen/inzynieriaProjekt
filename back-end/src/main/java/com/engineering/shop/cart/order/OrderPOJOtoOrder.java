package com.engineering.shop.cart.order;

import com.engineering.shop.common.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderPOJOtoOrder implements Transformer<OrderPOJO, Order> {

    // Docelowo dodac repo od danych uzytkownika ale nie wiem kto to dostarcza,
    // czy sobie sami mamy uklepac

    @Autowired
    public OrderPOJOtoOrder() {
    }

    @Override
    public Order transform(OrderPOJO pojo) {
        return Order.builder()
                .userId(pojo.getUserId())
                .firstName(pojo.getFirstName())
                .lastName(pojo.getLastName())
                .adress(pojo.getAdress())
                .phoneNumber(pojo.getPhoneNumber())
                .orderBucketId(pojo.getOrderBucketId())
                .orderValue(pojo.getOrderValue())
                .isPaid(pojo.getIsPaid())
                .build();
    }
}

package com.engineering.shop.cart.order;



import com.sun.istack.NotNull;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
//@Data
//@RequiredArgsConstructor
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY) // przy tworzeniu tabeli pojawia sie ostrzezenie, ze moze nie
    @Id                                             // stworzyc tabeli   o.h.t.s.i.ExceptionHandlerLoggedImpl     : GenerationTarget encountered exception accepting command : Error executing DDL "create table order
    private long orderId;

    @NotNull
    private Integer userId;
    // Zakladalismy przekazanie klasy CustomerDetails
    @NotNull
    private Integer customerId;
    @NotNull
    private Integer orderBucketId;
    @NotNull
    private BigDecimal orderValue;
    private String isPaid;
    //private PaymentType paymentType;

    public Order(){

    }

    public Order(Integer userId,Integer customerId, Integer orderBucketId, BigDecimal orderValue) {
        this.userId = userId;
        this.customerId = customerId;
        this.orderBucketId = orderBucketId;
        this.orderValue = orderValue;
    }



}

package com.engineering.shop.cart.order;

import org.springframework.validation.Validator;

public class OrderValidator {//} implements Validator {

    OrderRepo orderRepo;

    public OrderValidator(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }


}

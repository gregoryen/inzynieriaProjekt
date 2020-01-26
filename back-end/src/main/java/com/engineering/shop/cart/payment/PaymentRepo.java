package com.engineering.shop.cart.payment;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepo extends CrudRepository <Payment, Integer> {

    Optional<Payment> findByOrderId (Integer orderId);

}

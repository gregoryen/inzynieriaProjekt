package com.engineering.shop.cart.order;


import org.apache.coyote.http11.filters.SavedRequestInputFilter;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepo extends CrudRepository<Order, Integer> {
    Iterable<Order> findAllByEmail(String email);
}



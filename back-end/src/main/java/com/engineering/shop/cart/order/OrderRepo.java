package com.engineering.shop.cart.order;


import org.springframework.data.repository.CrudRepository;

public interface OrderRepo extends CrudRepository<Order, Integer> {

   //Iterable<Order> findOrderById(Integer id);

}



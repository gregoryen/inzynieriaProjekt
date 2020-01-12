package com.engineering.shop.cart.order;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;



import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderRepo orderRepo;
    private OrderPOJOtoOrder orderPOJOtoOrder;

    @Autowired
    public OrderController(OrderRepo orderRepo, OrderPOJOtoOrder orderPOJOtoOrder) {
        this.orderRepo = orderRepo;
        this.orderPOJOtoOrder = orderPOJOtoOrder;
    }


    // Repo methods implementation

    @PostMapping
    public void addOrder(@RequestBody OrderPOJO orderPOJO){
        Order order = orderPOJOtoOrder.transform(orderPOJO);
        orderRepo.save(order);
    }

    @GetMapping("/all")
    public Iterable<Order> getAll(){
        return orderRepo.findAll();
    }

    @GetMapping(path="{id}")
    public Optional<Order> getById(@PathVariable("id") Integer id) {
        return orderRepo.findById(id);
    }

    @PutMapping("update/{id}")
    public void updateOrder(@PathVariable("id") Integer id
                                            ,@RequestBody OrderPOJO orderPOJO) {
       Order order = orderPOJOtoOrder.transform(orderPOJO);
       orderRepo.save(order);
    }

    @DeleteMapping(path = "{id}")
    public void deleteById(@PathVariable("id") Integer id){
        orderRepo.deleteById(id);
    }


}

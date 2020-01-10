package com.engineering.shop.cart.order;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;



import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderRepo orderRepo;

    @Autowired
    public OrderController(OrderRepo orderRepo) {
        this.orderRepo = orderRepo;
    }

    // Repo methods implementation
    @GetMapping("/all")
    public Iterable<Order> getAll(){
        return orderRepo.findAll();
    }

    @GetMapping(path="{id}")
    public Optional<Order> getById(@PathVariable("id") Integer id) {
        return orderRepo.findById(id);
    }

    @PostMapping
    public Order addBucketPosition(Order order){
        return orderRepo.save(order);
    }

    @DeleteMapping(path = "{id}")
    public void deleteById(@PathVariable("id") Integer id){
        orderRepo.deleteById(id);
    }


}

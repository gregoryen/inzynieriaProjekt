package com.engineering.shop.cart.order;


import com.engineering.shop.cart.Exceptions.BucketException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderRepo orderRepo;
    private OrderPOJOtoOrder orderPOJOtoOrder;
    private OrderPOJOValidator orderValidator;

    @Autowired
    public OrderController(OrderRepo orderRepo,
                           OrderPOJOtoOrder orderPOJOtoOrder,
                           OrderPOJOValidator orderValidator) {
        this.orderRepo = orderRepo;
        this.orderPOJOtoOrder = orderPOJOtoOrder;
        this.orderValidator = orderValidator;
    }

    @PostMapping("/addOrder")
    public @ResponseBody Integer addOrder(@RequestBody @Validated OrderPOJO orderPOJO){
        Order order = orderPOJOtoOrder.transform(orderPOJO);
        orderRepo.save(order);
        return order.getOrderId();
    }

    @GetMapping("/all")
    public Iterable<Order> getAll(){
        return orderRepo.findAll();
    }

    @GetMapping(path="{id}")
    public Order getById(@PathVariable("id") Integer id) {
        Optional<Order> optOrder = Optional.ofNullable(orderRepo.findById(id)).orElseThrow(()-> new BucketException("Order not found with provided  id"));
        return optOrder.get();
    }

    @PutMapping("update/{id}")
    public void updateOrder(@PathVariable("id") Integer id
                                            ,@RequestBody OrderPOJO orderPOJO) {
       Order order = orderPOJOtoOrder.transform(orderPOJO);
       orderRepo.save(order);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        orderRepo.deleteById(id);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getCode();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(orderValidator);
    }


}

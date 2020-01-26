package com.engineering.shop.cart.payment;

import com.engineering.shop.cart.Exceptions.BucketException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    PaymentRepo paymentRepo;
    PaymentPOJOtoPayment paymentPOJOtoPayment;

    @Autowired
    public PaymentController(PaymentRepo paymentRepo, PaymentPOJOtoPayment paymentPOJOtoPayment) {
        this.paymentRepo = paymentRepo;
        this.paymentPOJOtoPayment = paymentPOJOtoPayment;
    }

    @PostMapping("/create")
    public @ResponseBody String createPayment(@RequestBody PaymentPOJO paymentPOJO){

        Payment payment = paymentPOJOtoPayment.transform(paymentPOJO);
        paymentRepo.save(payment);

        return "Payment done and saved !";
    }

    @GetMapping("/getAll")
    public Iterable<Payment> getAllPayment(){
        return paymentRepo.findAll();
    }

    @GetMapping("/getById/{id}")
    public Payment getById(@PathVariable("id") Integer id){
        Optional<Payment> optPayment = Optional.ofNullable(paymentRepo.findById(id)).orElseThrow(()-> new BucketException("Payment not found"));
        Payment payment = optPayment.get();

        return payment;
    }

    @GetMapping("/getByOrderId/{id}")
    public Payment getByOrderId(@PathVariable("id") Integer id){
        Optional<Payment> optPayment = Optional.ofNullable(paymentRepo.findByOrderId(id)).orElseThrow(()-> new BucketException("Payment not found"));
        Payment payment = optPayment.get();

        return payment;
    }
}

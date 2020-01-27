package com.engineering.shop.cart.order;

import com.engineering.shop.cart.Exceptions.BucketException;
import com.engineering.shop.cart.bucket.Bucket;
import com.engineering.shop.cart.bucket.BucketRepo;
import com.engineering.shop.common.Transformer;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.Optional;

@Service
public class OrderPOJOtoOrder implements Transformer<OrderPOJO, Order> {

    // Docelowo dodac repo od danych uzytkownika ale nie wiem kto to dostarcza,
    // czy sobie sami mamy uklepac

    BucketRepo bucketRepo;

    @Autowired
    public OrderPOJOtoOrder(BucketRepo bucketRepo) {
        this.bucketRepo = bucketRepo;
    }


    @Override
    public Order transform(OrderPOJO pojo) {
        return Order.builder()
                .firstName(pojo.getFirstName())
                .lastName(pojo.getLastName())
                .adress(pojo.getAdress())
                .email((pojo.getEmail()))
                .phoneNumber(pojo.getPhoneNumber())
                .bucket(getBucket(pojo.getOrderBucketId())) // dodac metode
                .orderValue(getOrderValue(pojo.getOrderBucketId()))
                .isPaid(pojo.getIsPaid())
                .build();
    }

    public Bucket getBucket (String token) {
        Optional<Bucket> optionalBucket = Optional.ofNullable(bucketRepo.findByToken(token)).orElseThrow(()-> new BucketException("Bucket not found with token !"));
        Bucket bucket = optionalBucket.get();
        return bucket;
    }

    public BigDecimal getOrderValue (String token) {
        Bucket bucket = getBucket(token);
        BigDecimal value = new BigDecimal(0);
        value = bucket.getTotalValue();
        return value;
    }
}

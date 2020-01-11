package com.engineering.shop.cart.order;

import com.engineering.shop.cart.bucket.Bucket;
import com.engineering.shop.cart.bucket.BucketRepo;
import com.engineering.shop.common.Transformer;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
                .userId(pojo.getUserId())
                .firstName(pojo.getFirstName())
                .lastName(pojo.getLastName())
                .adress(pojo.getAdress())
                .phoneNumber(pojo.getPhoneNumber())
                .bucket(getBucket(pojo.getOrderBucketId())) // dodac metode
                .orderValue(pojo.getOrderValue())
                .isPaid(pojo.getIsPaid())
                .build();
    }

    public Bucket getBucket (Integer id) {
        Optional<Bucket> optionalBucket = Optional.ofNullable(bucketRepo.findById(id)).orElseThrow();
        Bucket bucket = optionalBucket.get();

        return bucket;
    }
}

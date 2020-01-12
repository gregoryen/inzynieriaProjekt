package com.engineering.shop.cart.bucket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/bucket")
public class BucketController {

    private BucketRepo bucketRepo;

    @Autowired
    public BucketController(BucketRepo bucketRepo) {
        this.bucketRepo = bucketRepo;
    }

    @GetMapping("/all")
    public Iterable<Bucket> getAll() {
        return bucketRepo.findAll();
    }

    @GetMapping("/getById/{id}")
    public Optional<Bucket> getById(@PathVariable("id") Integer id) {
        return bucketRepo.findById(id);
    }

}

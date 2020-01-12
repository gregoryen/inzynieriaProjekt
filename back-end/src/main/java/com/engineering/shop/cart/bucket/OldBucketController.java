//package com.engineering.shop.cart.bucket;
//
//import com.engineering.shop.cart.Exceptions.BucketException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.rest.webmvc.ResourceNotFoundException;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/bucket")
//public class BucketController {
//
//    private BucketRepo bucketRepo;
//
//    @Autowired
//    public BucketController(BucketRepo bucketRepo) {
//        this.bucketRepo = bucketRepo;
//    }
//
//    //@GetMapping("/all")
//    public Iterable<Bucket> getAll() {
//        return bucketRepo.findAll();
//    }
//
//   // @GetMapping("/getById/{id}")
//    public Bucket getById(@PathVariable("id") Integer id) {
//        Optional<Bucket> optBucket = Optional.ofNullable(bucketRepo.findById(id)).orElseThrow(()-> new BucketException("Bucket not found with provided  id"));
//        Bucket bucket = optBucket.get();
//        return bucket;
//    }
//
//}

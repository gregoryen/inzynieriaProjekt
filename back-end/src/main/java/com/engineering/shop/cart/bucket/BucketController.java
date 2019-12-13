package com.engineering.shop.cart.bucket;


import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/bucket")
public class BucketController {

    private BucketRepo bucketRepo;

    public BucketController(BucketRepo bucketRepo) {
        this.bucketRepo = bucketRepo;
    }

    // Repo methods implementation
    @GetMapping("/all")
    public Iterable<Bucket> getAll(){
        return bucketRepo.findAll();
    }

    @GetMapping(path="{id}")
    public Optional<Bucket> getById(@PathVariable("id") Integer id) {
        return bucketRepo.findById(id);
    }

    @PostMapping("/add")
    public Bucket addBucketPosition(@RequestBody Bucket bucket){
        return bucketRepo.save(bucket);
    }

    @DeleteMapping(path = "{id}")
    public void deleteById(@PathVariable("id") Integer id){
        bucketRepo.deleteById(id);
    }
}

package com.engineering.shop.cart.bucketlist;


import com.engineering.shop.cart.bucketlist.BucketPosition;
import com.engineering.shop.cart.bucketlist.BucketPositionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/bucketPosition")
public class BucketPositionController {

    private BucketPositionRepo bucketPositionRepo;

    @Autowired
    public BucketPositionController(BucketPositionRepo bucketPositionRepo) {
        this.bucketPositionRepo = bucketPositionRepo;
    }

    // Repo methods implementation
    @GetMapping("/all")
    public Iterable<BucketPosition> getAll(){
        return bucketPositionRepo.findAll();
    }

    // metoda testowa
    @GetMapping("/test")
    public @ResponseBody String test(){
        return "Request Done";
    }

    @GetMapping(path="{id}")
    public BucketPosition getBucketPossitionById(@PathVariable("id") Integer id) {
        BucketPosition bucketPosition = bucketPositionRepo.findByBucketPositionId(id);
        return bucketPosition;
    }

    @PostMapping(path="/add")
    public @ResponseBody String addBucketPosition(@RequestBody BucketPosition bucketPosition){
        bucketPositionRepo.save(bucketPosition);
        return "Saved!";
    }


    @DeleteMapping(path = "{id}")
    public void deleteById(@PathVariable("id") Integer id){
        bucketPositionRepo.deleteById(id);
    }



}

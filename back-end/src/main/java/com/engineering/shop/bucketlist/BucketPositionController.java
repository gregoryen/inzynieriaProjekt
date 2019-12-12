package com.engineering.shop.bucketlist;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/bucketPosition")
public class BucketPositionController {

    private BucketPositionRepo bucketPositionRepo;

    @Autowired
    public BucketPositionController(BucketPositionRepo bucketPositionRepo) {

        this.bucketPositionRepo = bucketPositionRepo;
    }

    @GetMapping("/all")
    public Iterable<BucketPosition> getAll(){
        return bucketPositionRepo.findAll();
    }

    @GetMapping(path="{id}")
    public Optional<BucketPosition> getById(@PathVariable("id") Integer id) {
        return bucketPositionRepo.findById(id);
    }

}

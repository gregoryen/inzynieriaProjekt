package com.engineering.shop.cart.bucketlist;

import com.engineering.shop.cart.bucketlist.BucketPosition;
import com.engineering.shop.cart.bucketlist.BucketPositionRepo;
import com.engineering.shop.cart.product.Product;
import com.engineering.shop.cart.product.ProductController;
import com.engineering.shop.cart.product.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/bucketPosition")
public class BucketPositionController {

    private BucketPositionRepo bucketPositionRepo;
    //private ProductController productController;
    private ProductRepo productRepo;

    @Autowired
    public BucketPositionController(BucketPositionRepo bucketPositionRepo) {
        this.bucketPositionRepo = bucketPositionRepo;
    }

    @CrossOrigin
    @GetMapping(path="/getByUUID/{uuid}")
    public @ResponseBody Iterable<BucketPosition> getBtUUID(@PathVariable("uuid") String uuid) {
        return bucketPositionRepo.findByBucketIndex(uuid);
    }

    // metoda testowa
    @GetMapping("/test")
    public @ResponseBody String test(){
        return "Request Done";
    }

    @PostMapping("/add")
    public @ResponseBody String addProduct(@RequestBody BucketPosition product){
       bucketPositionRepo.save(product);
       return "Saved!";
    }

    @GetMapping("/all")
    public Iterable<BucketPosition> getAll(){
        return bucketPositionRepo.findAll();
    }





}

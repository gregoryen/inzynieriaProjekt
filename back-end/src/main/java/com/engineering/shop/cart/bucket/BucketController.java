package com.engineering.shop.cart.bucket;

import com.engineering.shop.cart.bucketlist.BucketProductController;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/bucket")
public class BucketController {

    private BucketRepo bucketRepo;
    private BucketProductController bucketProductControllerRepo;

    public BucketController(BucketRepo bucketRepo, BucketProductController bucketProductControllerRepo) {
        this.bucketRepo = bucketRepo;
        this.bucketProductControllerRepo = bucketProductControllerRepo;
    }

    // Repo methods implementation

    @GetMapping("/all")
    public Iterable<Bucket> getAll(){
        return bucketRepo.findAll();
    }

    @GetMapping("/getById/{id}")
    public Optional<Bucket> getById(@PathVariable("id") Integer id) {
        return bucketRepo.findById(id);
    }

    @GetMapping("getByUUID/{uuid}")
    public Optional<Bucket> getNyUUID(@PathVariable("uuid")String id){
        return bucketRepo.findByBucketIndex(id);
    }

//    @PostMapping("/addById/{id}")
//    public @ResponseBody String addBucketPositionById (@PathVariable("id") Integer id) {
//        Optional<BucketPosition> bucketOptional = Optional.ofNullable(bucketRepo.findById(id).orElseThrow());
//        Product product = productcOptional.get();
//
//        BucketPosition p = new BucketPosition(product.getProductId(), 1, product.getProductPrice());
//        bucketPositionRepo.save(p);
//        //bucketProductPositionRepo.addProductBtId(id);
//
//
//        return "Product Add to the bucket ";
//    }

    @DeleteMapping("delete/{id}")
    public void deleteById(@PathVariable("id") Integer id){
        bucketRepo.deleteById(id);
    }
}

package com.engineering.shop.cart.bucketlist;

import com.engineering.shop.cart.Exceptions.BucketException;
import com.engineering.shop.cart.bucketlist.BucketPosition;
import com.engineering.shop.cart.bucketlist.BucketPositionRepo;
import com.engineering.shop.products.ProductPOJO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.engineering.shop.products.ProductsRepo;

import java.util.Optional;

@RestController
@RequestMapping("/bucketPosition")
public class BucketPositionController {

    private BucketPositionRepo bucketPositionRepo;
    private ProductsRepo productRepo;
    private BucketPositionPOJOtoBucketPosition bucketPositionPOJOtoBucketPosition;

    public BucketPositionController(BucketPositionRepo bucketPositionRepo, ProductsRepo productRepo, BucketPositionPOJOtoBucketPosition bucketPositionPOJOtoBucketPosition) {
        this.bucketPositionRepo = bucketPositionRepo;
        this.productRepo = productRepo;
        this.bucketPositionPOJOtoBucketPosition = bucketPositionPOJOtoBucketPosition;
    }

    @CrossOrigin
    @PostMapping("/add")
    public @ResponseBody String addProduct(@RequestBody BucketPositionPOJO position){
       BucketPosition  bucketPosition = bucketPositionPOJOtoBucketPosition.transform(position);
       bucketPositionRepo.save(bucketPosition);
       return "Saved!";
    }
    @GetMapping("/all")
    public Iterable<BucketPosition> getAll(){
        return bucketPositionRepo.findAll();
    }
    @DeleteMapping("/delete/{id}")
    public void deletePositionById(@PathVariable("id") Integer id){
        bucketPositionRepo.deleteById(id);
    }

    @PutMapping("/update/{id}/{quantity}")
    public void changeQuantity(@PathVariable("id") Integer id, @PathVariable("quantity") Integer quantity){

        Optional<BucketPosition> bucketPositionOptional = Optional.ofNullable((bucketPositionRepo.findById(id))).orElseThrow(()-> new BucketException("Bucket not found with provided  id"));
        BucketPosition bucketPosition = bucketPositionOptional.get();

        bucketPosition.setProductQuantity(quantity);

    }




}

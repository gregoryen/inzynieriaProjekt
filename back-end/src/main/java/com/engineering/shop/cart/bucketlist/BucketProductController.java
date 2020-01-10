package com.engineering.shop.cart.bucketlist;

import com.engineering.shop.cart.bucket.Bucket;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import com.engineering.shop.products.Product;
import com.engineering.shop.products.ProductsRepo;

import java.util.Optional;

@RestController
@RequestMapping("/bucketProduct")
public class BucketProductController {

    BucketPositionRepo bucketPositionRepo;
    ProductsRepo productRepo;

    public BucketProductController(BucketPositionRepo bucketPositionRepo, ProductsRepo productRepo){
        this.bucketPositionRepo = bucketPositionRepo;
        this.productRepo = productRepo;
    }

    @PostMapping("/addById/{id}")
   // public @ResponseBody
    public void addProductBtId(@PathVariable("id") Integer id){
        Optional<Product> productcOptional = Optional.ofNullable(productRepo.findById(id).orElseThrow());
        Product product = productcOptional.get();
        Bucket bucket = new Bucket();
        BucketPosition p = new BucketPosition(product.getId(),
                1, product.getPrice());

        bucketPositionRepo.save(p);

       // return "Product add !";
    }

    @DeleteMapping("/deleteById/{id}")
    public void deleteBucketPositionById(@PathVariable("id") int id){
        bucketPositionRepo.deleteById(id);
    }



}

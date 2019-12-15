package com.engineering.shop.cart.bucketlist;

import com.engineering.shop.cart.product.Product;
import com.engineering.shop.cart.product.ProductRepo;
import net.minidev.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/bucketProduct")
public class BucketProductPosition {

    BucketPositionRepo bucketPositionRepo;
    ProductRepo productRepo;

    public BucketProductPosition (BucketPositionRepo bucketPositionRepo, ProductRepo productRepo){
        this.bucketPositionRepo = bucketPositionRepo;
        this.productRepo = productRepo;
    }

    @PostMapping("/addById/{id}")
   // public @ResponseBody
    public void addProductBtId(@PathVariable("id") Integer id){
        Optional<Product> productcOptional = Optional.ofNullable(productRepo.findById(id).orElseThrow());
        Product product = productcOptional.get();

        BucketPosition p = new BucketPosition(product.getProductId(), 1, product.getProductPrice());
        bucketPositionRepo.save(p);

       // return "Product add !";
    }



}

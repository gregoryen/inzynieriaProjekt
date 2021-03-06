package com.engineering.shop.cart.bucket;


import com.engineering.shop.cart.Exceptions.BucketException;

import com.engineering.shop.cart.bucketlist.*;


import com.engineering.shop.cart.order.OrderRepo;
import com.engineering.shop.common.Exceptions.ResourceNotFoundException;
import com.engineering.shop.products.Product;
import com.engineering.shop.products.ProductsRepo;


import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/bucket")
public class BucketController {

    BucketRepo bucketRepo;
    BucketPositionRepo bucketPositionRepo;
    ProductsRepo productsRepo;
    BucketPositionPOJOtoBucketPosition bucketPositionPOJOtoBucketPosition;


    public BucketController(BucketRepo bucketRepo,
                            BucketPositionRepo bucketPositionRepo,
                            ProductsRepo productsRepo,
                            BucketPositionPOJOtoBucketPosition bucketPositionPOJOtoBucketPosition ){

        this.bucketRepo = bucketRepo;
        this.bucketPositionRepo = bucketPositionRepo;
        this.productsRepo = productsRepo;
        this.bucketPositionPOJOtoBucketPosition = bucketPositionPOJOtoBucketPosition;

    }

    @PostMapping("/createBucket")
    public @ResponseBody String createBucketWithId (@RequestBody BucketPOJO bucketPOJO) {

        String token = bucketPOJO.getId();
        Boolean isInBase = bucketRepo.existsByToken(token);

        Bucket bucket;

        if (isInBase) {
            return "Bucket already exists";
        } else {
            bucket = new Bucket(token);
            bucketRepo.save(bucket);
        }

        return "Bucket created at id: " + token;
    }

    @PostMapping("/addProduct")
    public @ResponseBody String addProductById (@RequestBody BucketPositionPOJO bucketPositionPOJO) {

        Integer productId = bucketPositionPOJO.getProduct();
        String token = bucketPositionPOJO.getBucket();

        Boolean isInBase = bucketRepo.existsByToken(token);

        Bucket bucket;
        BucketPosition position;
        BigDecimal total;

        if (isInBase) {
            bucket = getBucketByToken(token);
        } else {
            bucket = new Bucket(token);
            bucketRepo.save(bucket);
        }

        Boolean isInBucket = bucketPositionRepo.existsByProductIdAndBucket(productId, bucket);


        if(isInBucket) {
           position = getBucketPositionByProductId(productId, bucket);
           total = position.getProductPrice().multiply(new BigDecimal(position.getProductQuantity()));
           bucket.substructFromTotalValue(total);
           position.setProductQuantity(bucketPositionPOJO.getProductQuantity());
           bucketPositionRepo.save(position);
        } else {
           position = bucketPositionPOJOtoBucketPosition.transform(bucketPositionPOJO);

        }

        Boolean isActive = isActive = position.getProduct().isActive();

        if(!isActive){
            // rzucic wyjatkiem
            return "Cannot add product to bucket, Product is not avaiable in the warehouse";
        }

        total = position.getProductPrice().multiply(new BigDecimal(position.getProductQuantity()));
        bucket.addToTotalValue(total);
        bucket.addToPositions(position);
        bucketRepo.save(bucket);
        if(!isInBucket){
            position.setBucket(bucket);
        }

       //  można by było jednak tu generowac id koszyka i zwracac
        return "save";
    }

    @GetMapping("/all")
    public Iterable<Bucket> findAll () {
        return bucketRepo.findAll();
    }

    @GetMapping("/itemNumber/{token}")
    public int getItemNumber(@PathVariable("token") String token) {
        try {
            return getBucketByToken(token).getUniqueItemsNumber();
        }
        catch (Exception ex) {
            return 0;
        }
    }


    @GetMapping("/getBucketById/{token}")
    public Bucket findByToken (@PathVariable("token") String token) {
        return getBucketByToken(token);
    }


    @DeleteMapping("/delete/{id}")
    public void deleteBucket (@PathVariable("id") String token){
        Bucket bucket = getBucketByToken(token);
        bucketRepo.delete(bucket);
    }

    @PutMapping("/update/{bucketId}/{productId}/{quantity}")
    public @ResponseBody Bucket updatePosition(@PathVariable("productId") Integer productId
                                                , @PathVariable("bucketId") String token
                                                , @PathVariable("quantity") Integer quantity){

        Bucket bucket = getBucketByToken(token);
        BucketPosition position = getBucketPositionByProductId(productId, bucket);

        position.setProductQuantity(quantity);
        Iterable<BucketPosition> productList = bucketPositionRepo.findByBucket(bucket);
        BigDecimal total = new BigDecimal(0);
        BigDecimal temp = new BigDecimal(0);

        for(BucketPosition itr: productList) {
            temp = itr.getProductPrice().multiply(new BigDecimal(itr.getProductQuantity()));
            total = total.add(temp);
        }

        bucket.setTotalValue(total);

        bucketRepo.save(bucket);
        return bucket;
    }

//    // usuwanie pozycji
    @DeleteMapping("/deletePosition/{bucketId}/{productId}")
    public @ResponseBody Bucket deletePosition(@PathVariable("productId") Integer productId
                                , @PathVariable("bucketId") String token){


      Bucket bucket = getBucketByToken(token);
    BucketPosition position = getBucketPositionByProductId(productId,bucket);

    System.out.println(position.getId());

    BigDecimal value = position.getProductPrice();
    value = value.multiply(new BigDecimal(position.getProductQuantity()));
    bucket.substructFromTotalValue(value);
    bucket.removeFromPositions(position);
    bucketRepo.save(bucket);

    return bucket;
}

    public Bucket getBucketByToken(String token) {
        Optional<Bucket> optBucket = Optional.ofNullable(bucketRepo.findByToken(token)).orElseThrow(()-> new BucketException("Bucket not found with provided  token"));
        Bucket bucket = optBucket.get();
        return bucket;
    }

    public BucketPosition getBucketPositionByProductId(Integer productId, Bucket bucket) {
        Optional<Product> optProduct = Optional.ofNullable(productsRepo.findById(productId)).orElseThrow(() -> new BucketException("Product not found with provided id"));
        Product product = optProduct.get();
        Optional<BucketPosition> optPosition = Optional.ofNullable(bucketPositionRepo.findBucketPositionByProductAndBucket(product, bucket))
                .orElseThrow(()-> new BucketException("Bucket position not found with provided  id"));
        return optPosition.get();
    }

}

package com.engineering.shop.cart.bucket;


import com.engineering.shop.cart.Exceptions.BucketException;

import com.engineering.shop.cart.bucketlist.BucketPosition;
import com.engineering.shop.cart.bucketlist.BucketPositionPOJO;
import com.engineering.shop.cart.bucketlist.BucketPositionPOJOtoBucketPosition;
import com.engineering.shop.cart.bucketlist.BucketPositionRepo;

import com.engineering.shop.cart.order.OrderRepo;

import com.engineering.shop.products.Product;
import com.engineering.shop.products.ProductsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/bucket")
public class BucketController {

    BucketRepo bucketRepo;
    BucketPositionRepo bucketPositionRepo;
    OrderRepo orderRepo;
    ProductsRepo productsRepo;
    BucketPositionPOJOtoBucketPosition bucketPositionPOJOtoBucketPosition;

    @Autowired
    public BucketController(BucketRepo bucketRepo, BucketPositionRepo bucketPositionRepo,
                          OrderRepo orderRepo, ProductsRepo productsRepo,
                          BucketPositionPOJOtoBucketPosition bucketPositionPOJOtoBucketPosition) {
        this.bucketRepo = bucketRepo;
        this.bucketPositionRepo = bucketPositionRepo;
        this.orderRepo = orderRepo;
        this.productsRepo = productsRepo;
        this.bucketPositionPOJOtoBucketPosition = bucketPositionPOJOtoBucketPosition;
    }

//     Wysylam Jsona
//    {
//        "product" : 1,
//        "productName": null, <- moze byc null bo i tak biore nazw i cene z produktu z bazy
//        "productPrice": null,
//        "productQuantity": 1,
//        "bucket": 1
//    }

    @PostMapping("addProduct")
    public @ResponseBody String addProductById (@RequestBody BucketPositionPOJO bucketPositionPOJO) {

        Integer productId = bucketPositionPOJO.getProduct();
        Integer bucketId = bucketPositionPOJO.getBucket();

        Boolean isInBase = bucketRepo.existsById(bucketId);

        Bucket bucket;
        BucketPosition position;
        BigDecimal total;

        if (isInBase) {
            bucket = getBucketById(bucketId);
        } else {
            bucket = new Bucket(bucketId);
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

    @GetMapping("/getBucketById/{bucketId}")
    public Optional<Bucket> findAll (@PathVariable("id") Integer id) {
        return bucketRepo.findById(id);
    }

    // usuwanie calego koszyka
    @DeleteMapping("/delete/{id}")
    public void deleteBucket (@PathVariable("id") Integer id){
        bucketRepo.deleteById(id);
    }

    @PutMapping("/update/{bucketId}/{productId}/{quantity}")
    public @ResponseBody String updatePosition(@PathVariable("productId") Integer productId
                                                , @PathVariable("bucketId") Integer bucketId
                                                , @PathVariable("quantity") Integer quantity){

        Bucket bucket = getBucketById(bucketId);
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
        return "Updated";
    }

    // usuwanie pozycji
    @DeleteMapping("/deletePosition/{bucketId}/{productId}")
    public @ResponseBody String deletePosition(@PathVariable("productId") Integer productId
                                , @PathVariable("bucketId") Integer bucketId){
        Bucket bucket = getBucketById(bucketId);
        BucketPosition position = getBucketPositionByProductId(productId,bucket);

        BigDecimal value = position.getProductPrice();
        value = value.multiply(new BigDecimal(position.getProductQuantity()));
        bucket.substructFromTotalValue(value);
        bucket.removeFromPositions(position);
        bucketRepo.save(bucket);
        bucketPositionRepo.delete(position);

        return "Position deleted";
    }

    public Bucket getBucketById(Integer id) {
        Optional<Bucket> optBucket = Optional.ofNullable(bucketRepo.findById(id)).orElseThrow(()-> new BucketException("Bucket not found with provided  id"));
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

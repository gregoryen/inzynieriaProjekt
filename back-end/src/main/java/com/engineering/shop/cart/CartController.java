package com.engineering.shop.cart;


import com.engineering.shop.cart.bucket.Bucket;
import com.engineering.shop.cart.bucket.BucketRepo;

import com.engineering.shop.cart.bucketlist.BucketPosition;
import com.engineering.shop.cart.bucketlist.BucketPositionPOJO;
import com.engineering.shop.cart.bucketlist.BucketPositionPOJOtoBucketPosition;
import com.engineering.shop.cart.bucketlist.BucketPositionRepo;

import com.engineering.shop.cart.order.OrderRepo;

import com.engineering.shop.products.ProductsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.StyledEditorKit;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/cart")
public class CartController {

    BucketRepo bucketRepo;
    BucketPositionRepo bucketPositionRepo;
    OrderRepo orderRepo;
    ProductsRepo productsRepo;
    BucketPositionPOJOtoBucketPosition bucketPositionPOJOtoBucketPosition;


//    StockAmountRepository stockAmountRepository;
//    StockAmountChangeRepository stockAmountChangeRepository;

    @Autowired
    public CartController(BucketRepo bucketRepo, BucketPositionRepo bucketPositionRepo, OrderRepo orderRepo, ProductsRepo productsRepo, BucketPositionPOJOtoBucketPosition bucketPositionPOJOtoBucketPosition) {
        this.bucketRepo = bucketRepo;
        this.bucketPositionRepo = bucketPositionRepo;
        this.orderRepo = orderRepo;
        this.productsRepo = productsRepo;
        this.bucketPositionPOJOtoBucketPosition = bucketPositionPOJOtoBucketPosition;
    }

    // Wysylam Jsona
//    {
//        "product" : 1,
//            "productName": null, <- moze byc null bo i tak biore nazw i cene z produktu z bazy
//            "productPrice": null,
//            "productQuantity": 1
//    }
    @PostMapping("addProduct/{bucketId}")
    public @ResponseBody String addProductById (@PathVariable("bucketId") Integer bucketId, @RequestBody BucketPositionPOJO bucketPositionPOJO) {

        Boolean isInBase = bucketRepo.existsById(bucketId);
        Bucket bucket;
        if (isInBase) {
            Optional<Bucket> optBucket = Optional.ofNullable(bucketRepo.findById(bucketId)).orElseThrow();
            bucket = optBucket.get();
        } else {
            bucket = new Bucket(bucketId);
        }

        BucketPosition position = bucketPositionPOJOtoBucketPosition.transform(bucketPositionPOJO);

        BigDecimal total = position.getProductPrice().multiply(new BigDecimal(position.getProductQuantity()));
        bucket.addToTotalValue(total);
        bucket.addToPositions(position);
        bucketRepo.save(bucket);
        position.setBucket(bucket);

        // można by było jednak tu generowac id koszyka i zwracac
        return "save";
    }

    @GetMapping("/all")
    public Iterable<Bucket> findAll () {
        return bucketRepo.findAll();
    }

    @DeleteMapping("/delete/{id}")
    public void deleteBucket (@PathVariable("id") Integer id){
        // dodac usuwanie pozycji rowniez jak usuwam koszyk
        bucketRepo.deleteById(id);
    }

    @DeleteMapping("/deletePosition/{positionId}")
    public void deletePosition(@PathVariable("id") Integer id){
        bucketPositionRepo.deleteById(id);
    }
}

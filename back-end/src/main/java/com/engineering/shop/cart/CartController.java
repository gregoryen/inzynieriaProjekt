package com.engineering.shop.cart;


import com.engineering.shop.cart.bucket.Bucket;
import com.engineering.shop.cart.bucket.BucketRepo;

import com.engineering.shop.cart.bucketlist.BucketPosition;
import com.engineering.shop.cart.bucketlist.BucketPositionPOJO;
import com.engineering.shop.cart.bucketlist.BucketPositionPOJOtoBucketPosition;
import com.engineering.shop.cart.bucketlist.BucketPositionRepo;

import com.engineering.shop.cart.order.OrderRepo;

import com.engineering.shop.products.ProductsRepo;

import org.hibernate.service.spi.OptionallyManageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.swing.text.StyledEditorKit;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;

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

    @GetMapping("/getBucketById/{bucketId}")
    public Optional<Bucket> findAll (@PathVariable("id") Integer id) {
        return bucketRepo.findById(id);
    }

    // usuwanie calego koszyka
    @DeleteMapping("/delete/{id}")
    public void deleteBucket (@PathVariable("id") Integer id){
        bucketRepo.deleteById(id);
    }

    @PutMapping("/update/{bucketId}/{positionId}/{quantity}")
    public @ResponseBody String updatePosition(@PathVariable("positionId") Integer positionId
                                                , @PathVariable("bucketId") Integer bucketId
                                                , @PathVariable("quantity") Integer quantity){

        Optional<BucketPosition> optPosition = Optional.ofNullable(bucketPositionRepo.findById(positionId)).orElseThrow();
        BucketPosition position = optPosition.get();

        Optional<Bucket> optBucket = Optional.ofNullable(bucketRepo.findById(bucketId)).orElseThrow();
        Bucket bucket = optBucket.get();
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
    @DeleteMapping("/deletePosition/{bucketId}/{positionId}")
    public @ResponseBody String deletePosition(@PathVariable("positionId") Integer positionId
                                , @PathVariable("bucketId") Integer bucketId){

        Optional<BucketPosition> optPosition = Optional.ofNullable(bucketPositionRepo.findById(positionId)).orElseThrow();
        BucketPosition position = optPosition.get();

        Optional<Bucket> optBucket = Optional.ofNullable(bucketRepo.findById(bucketId)).orElseThrow();
        Bucket bucket = optBucket.get();
        BigDecimal value = position.getProductPrice();
        value = value.multiply(new BigDecimal(position.getProductQuantity()));
        bucket.substructFromTotalValue(value);
        bucket.removeFromPositions(position);
        bucketRepo.save(bucket);
        bucketPositionRepo.delete(position);

        return "Position deleted";
    }

}

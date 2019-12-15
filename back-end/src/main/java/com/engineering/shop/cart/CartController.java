package com.engineering.shop.cart;

import com.engineering.shop.cart.bucket.Bucket;
import com.engineering.shop.cart.bucket.BucketController;
import com.engineering.shop.cart.bucket.BucketRepo;
import com.engineering.shop.cart.bucketlist.BucketPosition;
import com.engineering.shop.cart.bucketlist.BucketPositionController;
import com.engineering.shop.cart.bucketlist.BucketPositionRepo;
import com.engineering.shop.cart.order.OrderController;
import com.engineering.shop.cart.order.OrderRepo;
import com.engineering.shop.cart.product.Product;
import com.engineering.shop.cart.product.ProductController;
import com.engineering.shop.cart.product.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

    BucketRepo bucketRepo;
    BucketPositionRepo bucketPositionRepo;
    OrderRepo orderRepo;

    //Tymczasowo docelowo moduł Bartka i Seby(?)

    ProductRepo productRepo;

    @Autowired
    public CartController(BucketRepo bucketRepo,
                          BucketPositionRepo bucketPositionRepo,
                          OrderRepo orderRepo,
                          ProductRepo productRepo) {
        this.bucketRepo = bucketRepo;
        this.bucketPositionRepo = bucketPositionRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    @PostMapping("addProductToNewCartWithId/{id}/{quantity}")
    public @ResponseBody String addProductById(@PathVariable("id") Integer id,
                                               @PathVariable("quantity") int quantity){

        Optional<Product> productcOptional = Optional.ofNullable(productRepo.findById(id).orElseThrow());
        Product product = productcOptional.get();
        int q = quantity;
        BucketPosition p = new BucketPosition(product.getProductId(),
                q, product.getProductPrice());

        bucketPositionRepo.save(p);

        Bucket bucket = new Bucket();
        bucket.increaseTotalValue(p.getProductPrice()*p.getProductQuantity());
        p.setBucketIndex(bucket.getBucketIndex());

        bucketRepo.save(bucket);

        return bucket.getBucketIndex();
    }

    // productId to jest identyfikator produktu w snesie np. Grabi, Nasion, Spodnii ogrodnczych, a nie id produktu w zamówieniu

    @PostMapping("addProductToExistingCartWithId/{bucketId}/{productId}/{quantity}")
    public @ResponseBody String addProductById(@PathVariable("bucketId") String bucketId,
                                               @PathVariable("productId") Integer id,
                                               @PathVariable("quantity") int quantity){

        Optional<Bucket> bucketOptional = Optional.ofNullable(bucketRepo.findByBucketIndex(bucketId)).orElseThrow();
        Optional<Product> productcOptional = Optional.ofNullable(productRepo.findById(id).orElseThrow());
        Product product = productcOptional.get();
        int q = quantity;

        BucketPosition pos = new BucketPosition(product.getProductId(),
                q, product.getProductPrice());

        bucketPositionRepo.save(pos);

        Bucket bucket = bucketOptional.get();
        bucket.increaseTotalValue(pos.getProductPrice()*pos.getProductQuantity());
        pos.setBucketIndex(bucket.getBucketIndex());

        bucketRepo.save(bucket);

        return bucket.getBucketIndex();
    }

    // productId to jest identyfikator produktu w snesie np. Grabi, Nasion, Spodnii ogrodnczych, a nie id produktu w zamówieniu

    @DeleteMapping("deleteProductFromBucketId/{bucketId}/{productId}")
    public @ResponseBody String deleteProductById(@PathVariable("bucketId") String bucketId, @PathVariable("productId") int productId){

        Optional<BucketPosition> optionalPosition = bucketPositionRepo.findByBucketIndexAndProductId(bucketId,productId);

        BucketPosition position = optionalPosition.get();

        Optional<Bucket> optionalBucket = Optional.ofNullable(bucketRepo.findByBucketIndex(bucketId)).orElseThrow();
        Bucket bucket = optionalBucket.get();
        bucket.decreaseTotalValue(position.getProductPrice() * position.getProductQuantity());

        int idToDel = position.getId();
        bucketPositionRepo.deleteById(idToDel);

        return "Done";
    }

    @DeleteMapping("deleteBucketById/{bucketId}")
    public String deleteBucketById(@PathVariable("bucketId") String bucketId){

        Optional<Bucket> optionalBucket = Optional.ofNullable(bucketRepo.findByBucketIndex(bucketId)).orElseThrow();
        Iterable<BucketPosition> optionalPositionList = bucketPositionRepo.findByBucketIndex(bucketId);

        for (BucketPosition pos : optionalPositionList){
            bucketPositionRepo.delete(pos);
        }

        bucketRepo.delete(optionalBucket.get());

        return "Done";

    }

    // productId to jest identyfikator produktu w snesie np. Grabi, Nasion, Spodnii ogrodnczych, a nie id produktu w zamówieniu

    @PostMapping("setProductQuantity/{bucketId}/{productId}/{quantity}")
    public @ResponseBody String setProductQuantity(@PathVariable("bucketId") String bucketId, @PathVariable("productId") Integer productId, @PathVariable("quantity") int quantity){

        String text = "";
        Optional<Bucket> optionalBucket = Optional.of(bucketRepo.findByBucketIndex(bucketId)).orElseThrow();
        Optional<BucketPosition> postion = bucketPositionRepo.findByBucketIndexAndProductId(bucketId,productId);
        postion.get().setProductQuantity(quantity);
        text += postion.get().getProductQuantity();
        optionalBucket.get().setTotalValue(postion.get().getProductPrice() * postion.get().getProductQuantity());

        bucketPositionRepo.save(postion.get());
        bucketRepo.save(optionalBucket.get());

        return text;
    }

    // productId to jest identyfikator produktu w snesie np. Grabi, Nasion, Spodnii ogrodnczych, a nie id produktu w zamówieniu





}

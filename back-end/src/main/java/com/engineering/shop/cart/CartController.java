package com.engineering.shop.cart;

import com.engineering.shop.cart.bucket.Bucket;
import com.engineering.shop.cart.bucket.BucketController;
import com.engineering.shop.cart.bucket.BucketRepo;
import com.engineering.shop.cart.bucketlist.BucketPosition;
import com.engineering.shop.cart.bucketlist.BucketPositionController;
import com.engineering.shop.cart.bucketlist.BucketPositionRepo;
import com.engineering.shop.cart.order.OrderController;
import com.engineering.shop.cart.order.OrderRepo;
import com.engineering.shop.products.Product;
import com.engineering.shop.products.ProductsController;
import com.engineering.shop.products.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cart")
public class CartController {

    BucketRepo bucketRepo;
    BucketPositionRepo bucketPositionRepo;
    OrderRepo orderRepo;
    ProductsRepo productRepo;

    @Autowired
    public CartController(BucketRepo bucketRepo,
                          BucketPositionRepo bucketPositionRepo,
                          OrderRepo orderRepo,
                          ProductsRepo productRepo) {
        this.bucketRepo = bucketRepo;
        this.bucketPositionRepo = bucketPositionRepo;
        this.orderRepo = orderRepo;
        this.productRepo = productRepo;
    }

    @PostMapping("addProduct/{id}/{quantity}")
    public @ResponseBody String addProductById(@PathVariable("id") Integer id,
                                               @PathVariable("quantity") int quantity){

        Optional<Product> productcOptional = Optional.ofNullable(productRepo.findById(id).orElseThrow());
        Product product = productcOptional.get();
        int q = quantity;
        BucketPosition p = new BucketPosition(product.getId(),
                q, product.getPrice());

        bucketPositionRepo.save(p);

        Bucket bucket = new Bucket();
        bucket.increaseTotalValue(p.getProductPrice().multiply(new BigDecimal(p.getProductQuantity())));
        p.setBucketIndex(bucket.getBucketIndex());

        bucketRepo.save(bucket);
        String responseBody = "";
        responseBody += bucket.getBucketIndex();
        return responseBody;
    }

    @PostMapping("addProduct/{bucketId}/{id}/{quantity}")
    public @ResponseBody String addProductById(@PathVariable("bucketId") String bucketId,
                                               @PathVariable("id") Integer id,
                                               @PathVariable("quantity") int quantity){

        Optional<Bucket> bucketOptional = Optional.ofNullable(bucketRepo.findByBucketIndex(bucketId)).orElseThrow();
        Optional<Product> productcOptional = Optional.ofNullable(productRepo.findById(id).orElseThrow());
        Product product = productcOptional.get();
        int q = quantity;

        BucketPosition pos = new BucketPosition(product.getId(),
                q, product.getPrice());

        bucketPositionRepo.save(pos);

        Bucket bucket = bucketOptional.get();
        bucket.increaseTotalValue(pos.getProductPrice().multiply(new BigDecimal(pos.getProductQuantity())));
        pos.setBucketIndex(bucket.getBucketIndex());

        bucketRepo.save(bucket);
        return "Dodano nowy element do koszyka";
    }



}

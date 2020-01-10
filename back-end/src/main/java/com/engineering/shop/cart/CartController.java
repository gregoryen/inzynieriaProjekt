package com.engineering.shop.cart;


import com.engineering.shop.cart.bucket.BucketRepo;

import com.engineering.shop.cart.bucketlist.BucketPositionRepo;

import com.engineering.shop.cart.order.OrderRepo;

import com.engineering.shop.products.ProductsRepo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    BucketRepo bucketRepo;
    BucketPositionRepo bucketPositionRepo;
    OrderRepo orderRepo;
    ProductsRepo productsRepo;

//    StockAmountRepository stockAmountRepository;
//    StockAmountChangeRepository stockAmountChangeRepository;

    @Autowired
    public CartController(BucketRepo bucketRepo, BucketPositionRepo bucketPositionRepo, OrderRepo orderRepo, ProductsRepo productsRepo) {
        this.bucketRepo = bucketRepo;
        this.bucketPositionRepo = bucketPositionRepo;
        this.orderRepo = orderRepo;
        this.productsRepo = productsRepo;
    }



}

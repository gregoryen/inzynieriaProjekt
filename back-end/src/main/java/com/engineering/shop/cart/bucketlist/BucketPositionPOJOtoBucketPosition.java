package com.engineering.shop.cart.bucketlist;

import com.engineering.shop.cart.bucket.Bucket;
import com.engineering.shop.cart.bucket.BucketRepo;
import com.engineering.shop.common.Transformer;
import com.engineering.shop.products.Product;
import com.engineering.shop.products.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BucketPositionPOJOtoBucketPosition implements Transformer<BucketPositionPOJO, BucketPosition> {

    private ProductsRepo productsRepo;
    private BucketRepo bucketRepo;

    @Autowired
    public BucketPositionPOJOtoBucketPosition(ProductsRepo productsRepo, BucketRepo bucketRepo){
        this.productsRepo = productsRepo;
        this.bucketRepo = bucketRepo;
    }

    @Override
    public BucketPosition transform(BucketPositionPOJO pojo) {
        return BucketPosition.builder()
                .product(getProduct(pojo.getProduct()))
               // .productId(pojo.getProduct())
                .productName(getProduct(pojo.getProduct()).getName())
                .productPrice(getProduct((pojo.getProduct())).getPrice())
                .productQuantity(pojo.getProductQuantity())
                .bucket(getBucket(pojo.getBucket()))
                .build();
    }

    private Product getProduct(Integer id) {
        Optional<Product> product = Optional.ofNullable(productsRepo.findById(id)).orElseThrow();
        Product productToReturn = product.get();
        return productToReturn;
    }

    private Bucket getBucket(Integer id){
        Bucket bucket;
        Boolean isTrue = bucketRepo.existsById(id);
        if(isTrue){
            Optional<Bucket> product = Optional.ofNullable(bucketRepo.findById(id)).orElseThrow();
            bucket = product.get();
        } else {
            bucket = new Bucket(id);
        }
        return bucket;
    }
}

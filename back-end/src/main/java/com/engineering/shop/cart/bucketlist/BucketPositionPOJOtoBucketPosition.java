package com.engineering.shop.cart.bucketlist;

import com.engineering.shop.common.Transformer;
import com.engineering.shop.products.Product;
import com.engineering.shop.products.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class BucketPositionPOJOtoBucketPosition implements Transformer<BucketPositionPOJO, BucketPosition> {

    private ProductsRepo productsRepo;

    @Autowired
    public BucketPositionPOJOtoBucketPosition(ProductsRepo productsRepo){
        this.productsRepo = productsRepo;
    }

    @Override
    public BucketPosition transform(BucketPositionPOJO pojo) {
        return BucketPosition.builder()
                .product(getProduct(pojo.getProduct()))
                .productName(getProduct(pojo.getProduct()).getName())
                .productPrice(getProduct((pojo.getProduct())).getPrice())
                .productQuantity(pojo.getProductQuantity())
                .build();
    }

    private Product getProduct(Integer id) {
        Optional<Product> product = Optional.ofNullable(productsRepo.findById(id)).orElseThrow();
        Product productToReturn = product.get();
        return productToReturn;
    }
}

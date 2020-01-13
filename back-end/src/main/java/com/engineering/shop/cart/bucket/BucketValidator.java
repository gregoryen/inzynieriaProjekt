package com.engineering.shop.cart.bucket;

import com.engineering.shop.cart.bucketlist.BucketPosition;
import com.engineering.shop.cart.bucketlist.BucketPositionPOJO;
import com.engineering.shop.cart.bucketlist.BucketPositionPOJOtoBucketPosition;
import com.engineering.shop.cart.bucketlist.BucketPositionRepo;
import com.engineering.shop.products.Product;
import com.engineering.shop.products.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Configuration
public class BucketValidator implements Validator {

    BucketRepo bucketRepo;
    BucketPositionRepo bucketPositionRepo;
    ProductsRepo productRepo;

    @Autowired
    public BucketValidator(BucketRepo bucketRepo,
                           BucketPositionRepo bucketPositionRepo,
                           ProductsRepo productRepo) {
        this.bucketRepo = bucketRepo;
        this.bucketPositionRepo = bucketPositionRepo;
        this.productRepo = productRepo;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return BucketPositionPOJO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

        ValidationUtils.rejectIfEmpty(errors,"product", "product.empty", "invalid product id");
        ValidationUtils.rejectIfEmpty(errors,"productQuantity", "productQuantity.empty","provide product quantity");
        ValidationUtils.rejectIfEmpty(errors,"bucket", "bucket.empty","invalid bucket token");

        BucketPositionPOJO bucket = (BucketPositionPOJO) o;

        Integer productId = bucket.getProduct();
        Optional<Product> optProduct = productRepo.findById(productId);

        if(optProduct.isEmpty()){
            errors.rejectValue("product","product with provided id doesn't exist");
        }

        if(!optProduct.get().isActive()){
            errors.rejectValue("product", "product is not currently avaiable");
        }

        if(bucket.getBucket()==null){
            errors.rejectValue("bucket token","null value");
        }

        if(bucket.getProductQuantity()==null){
            errors.rejectValue("productQuantity","null value");
        }

        if(bucket.getProduct()==null){
            errors.rejectValue("product","null value");
        }

        if(bucket.getBucket().isEmpty()){
            errors.rejectValue("bucket token","bucket token must be provided");
        }

        if(bucket.getProductQuantity()<=0){
            errors.rejectValue("productQuantity","quantity must be positive");
        }




    }
}

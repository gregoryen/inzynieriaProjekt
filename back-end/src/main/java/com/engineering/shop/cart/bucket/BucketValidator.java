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

        ValidationUtils.rejectIfEmpty(errors,"bucket", "token must be provided.");
        ValidationUtils.rejectIfEmpty(errors,"productQuantity", "quantity must be provided");
        ValidationUtils.rejectIfEmpty(errors,"product", "product must be provided");

        BucketPositionPOJO bucket = (BucketPositionPOJO) o;

        if(bucket.getBucket().isEmpty()){
            errors.rejectValue("bucket token","bucket token must be provided");
        }

        if(bucket.getProductQuantity()>0){
            errors.rejectValue("productQuantity","quantity must be positive");
        }

        Optional<Product> optProduct = productRepo.findById(bucket.getProduct());
        if(optProduct.isEmpty()){
            errors.rejectValue("product","product with provided id doesn't exist");
        }

        if(!optProduct.get().isActive()){
            errors.rejectValue("product", "product is not currently avaiable");
        }




    }
}

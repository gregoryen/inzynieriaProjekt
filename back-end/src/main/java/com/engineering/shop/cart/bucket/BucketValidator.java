package com.engineering.shop.cart.bucket;

import com.engineering.shop.cart.bucketlist.BucketPositionPOJO;
import com.engineering.shop.cart.bucketlist.BucketPositionRepo;
import com.engineering.shop.products.Product;
import com.engineering.shop.products.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

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


        ValidationUtils.rejectIfEmpty(errors,"product", "bucket.product.empty", "invalid product id");
        ValidationUtils.rejectIfEmpty(errors,"productQuantity", "bucket.productQuantity.empty","provide product quantity");
        ValidationUtils.rejectIfEmpty(errors,"bucket", "bucket.bucket.empty","invalid bucket token");

        BucketPositionPOJO bucketPos = (BucketPositionPOJO) o;

        Integer productId = bucketPos.getProduct();
        Optional<Product> optProduct = productRepo.findById(productId);
        System.out.println(optProduct.isEmpty());
        // to nie dziala ??
        if(optProduct.isEmpty()){
            System.out.println("Tutaj jestem");
            errors.rejectValue("product","product with provided id doesn't exist");
        }

        if(!optProduct.get().isActive()){
            errors.rejectValue("product", "product is not currently avaiable");
        }

        if(bucketPos.getBucket()==null){
            errors.rejectValue("bucket","null value");
        }

        if(bucketPos.getProductQuantity()==null){
            errors.rejectValue("productQuantity","null value");
        }

        if(bucketPos.getProduct()==null){
            errors.rejectValue("product","null value");
        }

        if(bucketPos.getBucket().isEmpty()){
            errors.rejectValue("bucket token","bucket token must be provided");
        }

        if(bucketPos.getProductQuantity()<=0){
            errors.rejectValue("productQuantity","quantity must be positive");
        }

    }
}

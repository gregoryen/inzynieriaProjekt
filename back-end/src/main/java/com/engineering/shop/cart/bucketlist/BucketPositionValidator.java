package com.engineering.shop.cart.bucketlist;

import com.engineering.shop.cart.bucket.Bucket;
import com.engineering.shop.products.Product;
import com.engineering.shop.products.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.Optional;

@Configuration
public class BucketPositionValidator implements Validator {

    private BucketPositionRepo bucketPositionRepo;
    private ProductsRepo productRepo;

    @Autowired
    public BucketPositionValidator(BucketPositionRepo bucketPositionRepo,
                                   ProductsRepo productRepo) {
        this.bucketPositionRepo = bucketPositionRepo;
        this.productRepo = productRepo;
    }

    @Override
    public boolean supports(Class<?> vClass) {
        return BucketPosition.class.equals(vClass);
    }

    @Override
    public void validate(Object obj, Errors errors){
        ValidationUtils.rejectIfEmpty(errors,"productQuantity", "productQuantity.empty");
        ValidationUtils.rejectIfEmpty(errors, "product", "product.empty" );
        ValidationUtils.rejectIfEmpty(errors, "bucket", "bucket.empty" );

        BucketPosition bucketPosition = (BucketPosition)obj;

        if(!bucketPosition.getProduct().isActive()){
            errors.rejectValue("isActive", "product must be avaiable in warhouse");
        }

        if(bucketPosition.getProductQuantity() <= 0){
            errors.rejectValue("productQuantity","must be larger then 0");
        }

        Integer product = bucketPosition.getProduct().getId();
        Optional<Product> optProduct = productRepo.findById(product);

        if(optProduct.isEmpty()){
            errors.rejectValue("bucketPosition.product", "Product with provided id not exists");
        }


    }

}

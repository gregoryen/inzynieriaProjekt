package com.engineering.shop.cart.order;

import com.engineering.shop.cart.bucket.Bucket;
import com.engineering.shop.cart.bucket.BucketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.regex.Pattern;
import java.util.Optional;

 @Configuration
public class OrderPOJOValidator implements Validator {

    OrderRepo orderRepo;
    BucketRepo bucketRepo;

    @Autowired
    public OrderPOJOValidator(OrderRepo orderRepo,
                              BucketRepo bucketRepo) {
        this.orderRepo = orderRepo;
        this.bucketRepo = bucketRepo;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderPOJO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "userId", "order.userId.empty");
        ValidationUtils.rejectIfEmpty(errors, "firstName", "order.firstName.empty");
        ValidationUtils.rejectIfEmpty(errors, "lastName", "order.lastName.empty");
        ValidationUtils.rejectIfEmpty(errors, "adress", "order.adress.empty");
        ValidationUtils.rejectIfEmpty(errors, "email", "order.email.empty");
        ValidationUtils.rejectIfEmpty(errors, "phoneNumber", "order.phoneNumber.empty");
        ValidationUtils.rejectIfEmpty(errors, "orderBucketId", "order.bucket.empty");
        ValidationUtils.rejectIfEmpty(errors, "orderValue", "order.orderValue.empty");

        OrderPOJO order = (OrderPOJO) o;

        Pattern email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE);

        Pattern phone = Pattern.compile("^[0-9]{9}$",
                Pattern.CASE_INSENSITIVE);

        if(!(email.matcher(order.getEmail()).matches())){
            errors.rejectValue("email","order.email.invalid");
        }

        if(!(phone.matcher(order.getPhoneNumber()).matches())){
            errors.rejectValue("phoneNumber","order.phone.invalid");
        }

        Optional<Bucket> optBucket = bucketRepo.findByToken(order.getOrderBucketId());
        if(optBucket.isEmpty()){
            errors.rejectValue("orderBucketId","bucket not exists");
        }

        if(order.getOrderValue().compareTo(new BigDecimal(0))<0){
            errors.rejectValue("orderValue","order.orderValue.mustBePositiveNumber");
        }

    }
}

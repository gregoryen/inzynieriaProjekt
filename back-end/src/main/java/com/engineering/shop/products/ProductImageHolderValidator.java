package com.engineering.shop.products;

import com.engineering.shop.categories.CategoriesRepo;
import com.engineering.shop.categories.Category;
import com.engineering.shop.imageProducts.ImageProductRepo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Optional;

@Configuration
public class ProductImageHolderValidator implements Validator {
    private CategoriesRepo categoriesRepo;
    private ImageProductRepo imageProductRepo;

    @Autowired
    public ProductImageHolderValidator(CategoriesRepo categoriesRepo, ImageProductRepo imageProductRepo) {
        this.categoriesRepo = categoriesRepo;
        this.imageProductRepo = imageProductRepo;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return ProductImageHolder.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "product.name", "name.empty");
        ValidationUtils.rejectIfEmpty(errors, "product.price", "price.empty");
        ValidationUtils.rejectIfEmpty(errors, "product.mainCategoryId", "mainCategoryId.empty");
        ValidationUtils.rejectIfEmpty(errors, "additionalImages", "additionalImages.empty");

        ProductImageHolder productImageHolder = (ProductImageHolder) o;
        ProductPOJO product = productImageHolder.getProduct();

        if (product.getMainCategoryId() != null) {
            Optional<Category> category = categoriesRepo.findById(product.getMainCategoryId());
            if (category.isEmpty()) {
                errors.rejectValue("product.mainCategoryId", "category not exists");
            }
        }

        Integer mainImage = product.getMainImage();
        if (mainImage != null) {
            if (imageProductRepo.findById(mainImage).isEmpty()) {
                errors.rejectValue("product.mainImage", "mainImage not exists");
            }
        }

        List<Integer> additionalImages = productImageHolder.getAdditionalImages();
        if (additionalImages != null) {
            if (CollectionUtils.isNotEmpty(additionalImages)) {
                for (Integer image : additionalImages) {
                    if (imageProductRepo.findById(image).isEmpty()) {
                        errors.rejectValue("additionalImages", "additionalImages not exists");
                    }
                }
            }
        }
    }
}

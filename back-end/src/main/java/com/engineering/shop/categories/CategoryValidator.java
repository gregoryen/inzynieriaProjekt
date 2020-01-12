package com.engineering.shop.categories;

import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

//@Component("beforeCreateCategoryValidator")
@Configuration
public class CategoryValidator implements Validator {
    private CategoriesRepo categoriesRepo;

    @Autowired
    public CategoryValidator(CategoriesRepo categoriesRepo) {
        this.categoriesRepo = categoriesRepo;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Category.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "name.empty");
        Category category = (Category) o;

        if (category.getPreviousCategoryId() != null) {
            Optional<Category> prevCategory = categoriesRepo.findById(category.getPreviousCategoryId());
            prevCategory.ifPresent(prev -> {
                if (!Objects.equals(category.getParentId(), prev.getParentId())) {
                    errors.rejectValue("previousCategoryId", "wrong value");
                }
            });
        }
    }
}

package com.engineering.shop.products;

import com.engineering.shop.categories.CategoriesRepo;
import com.engineering.shop.categories.Category;
import com.engineering.shop.common.Transformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductPOJOToProductTransformer implements Transformer<ProductPOJO, Product> {

    private CategoriesRepo categoriesRepo;

    @Autowired
    public ProductPOJOToProductTransformer(CategoriesRepo categoriesRepo) {
        this.categoriesRepo = categoriesRepo;
    }

    @Override
    public Product transform(ProductPOJO pojo) {
        return Product.builder()
                .name(pojo.getName())
                .description(pojo.getDescription())
                .isbn(pojo.getIsbn())
                .ean13(pojo.getEan13())
                .price(pojo.getPrice())
                .reference(pojo.getReference())
                .categories(getCategories(pojo.getCategories()))
                .mainCategoryId(pojo.getMainCategoryId())
                .build();
    }

    private Set<Category> getCategories(Set<Integer> ids) {
        return ids.stream().map(id -> categoriesRepo.findById(id)).filter(Optional::isPresent).map(Optional::get).collect(Collectors.toSet());
    }
}

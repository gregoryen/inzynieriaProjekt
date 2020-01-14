package com.engineering.shop.quickSearch;

import com.engineering.shop.categories.CategoriesRepo;
import com.engineering.shop.products.Product;
import com.engineering.shop.products.ProductHeaderProjection;
import com.engineering.shop.products.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuickSearch {
    private CategoriesRepo categoriesRepo;
    private ProductsRepo productsRepo;

    @Autowired
    public QuickSearch(CategoriesRepo categoriesRepo, ProductsRepo productsRepo) {
        this.categoriesRepo = categoriesRepo;
        this.productsRepo = productsRepo;
    }

    public Iterable<Product> search(String phrase) {
        return productsRepo.findAllByNameContainingIgnoreCaseAndActiveIsTrue(phrase);
    }
}

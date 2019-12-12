package com.engineering.shop.quickSearch;

import com.engineering.shop.categories.CategoriesRepo;
import com.engineering.shop.products.Product;
import com.engineering.shop.products.ProductHeader;
import com.engineering.shop.products.ProductToProductHeaderTransformer;
import com.engineering.shop.products.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class QuickSearch {
    private CategoriesRepo categoriesRepo;
    private ProductsRepo productsRepo;
    private ProductToProductHeaderTransformer transformer;

    @Autowired
    public QuickSearch(CategoriesRepo categoriesRepo, ProductsRepo productsRepo, ProductToProductHeaderTransformer transformer) {
        this.categoriesRepo = categoriesRepo;
        this.productsRepo = productsRepo;
        this.transformer = transformer;
    }

    public Iterable<ProductHeader> search(String phrase) {
        Iterable<Product> products = productsRepo.findByNameContainingIgnoreCase(phrase);
        Map<Integer, List<Product>> productsByCategory = StreamSupport.stream(products.spliterator(), false).filter(Product::isActive).collect(Collectors.groupingBy(Product::getMainCategoryId));
        return productsByCategory.values().stream().flatMap(Collection::stream).map(transformer::transform).collect(Collectors.toList());
    }
}

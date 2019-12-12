package com.engineering.shop.categories;

import com.engineering.shop.common.Exceptions.ResourceNotFoundException;
import com.engineering.shop.products.Product;
import com.engineering.shop.products.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

@RestController
public class CategoriesProductsController {

    private CategoriesRepo categoriesRepo;
    private ProductsRepo productsRepo;

    @Autowired
    public CategoriesProductsController(CategoriesRepo categoriesRepo, ProductsRepo productsRepo) {
        this.categoriesRepo = categoriesRepo;
        this.productsRepo = productsRepo;
    }

    @GetMapping("/products/branch/{id}")
    public Iterable<Category> getBranchByProductId(@PathVariable int id) {
        Optional<Product> productOptional = Optional.ofNullable(productsRepo.getById(id)).orElseThrow(ResourceNotFoundException::new);
        Product product = productOptional.get();
        List<Category> categories = getCategoriesForProduct(product);
        categories.forEach(c -> c.add(ControllerLinkBuilder.linkTo(Category.class).slash(c.getId()).withSelfRel()));
        return categories;
    }

    private List<Category> getCategoriesForProduct(Product product) throws ResourceNotFoundException {
        Optional<Category> category = getCategoryById(product.getMainCategoryId());

        Stack<Category> categories = new Stack<>();
        categories.push(category.get());
        while (category.get().getParentId() != null) {
            category = getCategoryById(category.get().getParentId());
            categories.push(category.get());
        }

        return new ArrayList<>(categories);
    }

    private Optional<Category> getCategoryById(Integer id) {
        return Optional.ofNullable(categoriesRepo.findById(id).orElseThrow(ResourceNotFoundException::new));
    }
}

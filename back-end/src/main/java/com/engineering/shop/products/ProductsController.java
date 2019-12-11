package com.engineering.shop.products;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private ProductsRepo productsRepo;

    @Autowired
    public ProductsController(ProductsRepo productsRepo) {
        this.productsRepo = productsRepo;
    }

    @GetMapping("/all")
    public Iterable<Product> getAll() {
        return productsRepo.findAll();
    }

    @GetMapping
    public Optional<Product> getById(@RequestParam Integer id) {
        return productsRepo.findById(id);
    }

    @GetMapping("/name")
    public Iterable<Product> getByName(@RequestParam String name) {
        return productsRepo.findByName(name);
    }

    @GetMapping("/category")
    public Iterable<Product> getByCategory(@RequestParam Integer categoryId) {
        return productsRepo.findByCategoryId(categoryId);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productsRepo.save(product);
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        return productsRepo.save(product);
    }

    @DeleteMapping
    public void deleteProductById(@RequestParam Integer id) {
        productsRepo.deleteById(id);
    }
}


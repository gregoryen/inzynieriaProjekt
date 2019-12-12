package com.engineering.shop.products;

import com.engineering.shop.fileUpload.controller.FileController;
import com.engineering.shop.fileUpload.service.FileStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    private ProductPOJOToProductTransformer productPOJOToProductTransformer;

    private FileStorageService fileStorageService;

    private ProductsRepo productsRepo;

    @Autowired
    public ProductsController(ProductPOJOToProductTransformer productPOJOToProductTransformer, FileStorageService fileStorageService, ProductsRepo productsRepo) {
        this.productPOJOToProductTransformer = productPOJOToProductTransformer;
        this.fileStorageService = fileStorageService;
        this.productsRepo = productsRepo;
    }

    @PostMapping
    public Product addProduct(@RequestBody ProductPOJO productPOJO) {
        Product product = productPOJOToProductTransformer.transform(productPOJO);
        return productsRepo.save(product);
    }
}


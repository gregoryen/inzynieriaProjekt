package com.engineering.shop.products;

import com.engineering.shop.imageProducts.ImageProduct;
import com.engineering.shop.imageProducts.ImageProductRepo;
import com.engineering.shop.products.exception.ProductCreateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

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


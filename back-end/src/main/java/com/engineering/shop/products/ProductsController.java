package com.engineering.shop.products;

import com.engineering.shop.fileUpload.controller.FileController;
import com.engineering.shop.fileUpload.service.FileStorageService;
import com.engineering.shop.imageProducts.ImageProduct;
import com.engineering.shop.imageProducts.ImageProductRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private ImageProductRepo imageProductRepo;

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

/*    @PostMapping
    public Product addProduct(@RequestParam("file") MultipartFile file, @RequestParam("product") Product product) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

*//*        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());*//*
        return productsRepo.save(product);
    }*/

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


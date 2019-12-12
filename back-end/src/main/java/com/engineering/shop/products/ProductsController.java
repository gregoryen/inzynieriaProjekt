package com.engineering.shop.products;

import com.engineering.shop.fileUpload.controller.FileController;
import com.engineering.shop.imageProducts.ImageProduct;
import com.engineering.shop.imageProducts.ImageProductRepo;
import com.engineering.shop.products.exception.ProductCreateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

/*    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productsRepo.save(product);
    }*/

    @PostMapping
    public Product addProduct(@RequestBody ProductImageHolder productImageHolder) {
        Product product = productImageHolder.getProduct();
        Integer mainImage = productImageHolder.getMainImage();
        List<Integer> additionalImages = productImageHolder.getAdditionalImages();

        if (imageProductRepo.findById(mainImage).isEmpty()) {
            throw new ProductCreateException("Sorry, error occurred while saving the images attached to the product. Please try again");
        }
        for (Integer image : additionalImages) {
            if (imageProductRepo.findById(image).isEmpty()) {
                throw new ProductCreateException("Sorry, error occurred while saving the images attached to the product. Please try again");
            }
        }

        Optional<ImageProduct> temp = imageProductRepo.findById(mainImage);
        temp.get().setIdProduct(product.getId());
        imageProductRepo.save(temp.get());

        product.setMainImageId(mainImage);

        for (Integer image : additionalImages) {
            temp = imageProductRepo.findById(image);
            temp.get().setIdProduct(product.getId());
            imageProductRepo.save(temp.get());
        }
        return productsRepo.save(product);
     //   return product;
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


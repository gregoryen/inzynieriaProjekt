package com.engineering.shop.products;

import com.engineering.shop.imageProducts.ImageProduct;
import com.engineering.shop.imageProducts.ImageProductRepo;
import com.engineering.shop.products.exception.ProductCreateException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private ProductPOJOToProductTransformer productPOJOToProductTransformer;

    private ProductsRepo productsRepo;

    private ImageProductRepo imageProductRepo;

    @Autowired
    public ProductsController(ProductPOJOToProductTransformer productPOJOToProductTransformer, ProductsRepo productsRepo, ImageProductRepo imageProductRepo) {
        this.productPOJOToProductTransformer = productPOJOToProductTransformer;
        this.productsRepo = productsRepo;
        this.imageProductRepo = imageProductRepo;
    }

    @CrossOrigin
    @PostMapping
    public Product addProduct(@RequestBody ProductImageHolder productImageHolder) {
        Product product = productPOJOToProductTransformer.transform(productImageHolder.getProduct());
        Integer mainImage = product.getMainImage();
        List<Integer> additionalImages = productImageHolder.getAdditionalImages();

        if (imageProductRepo.findById(mainImage).isEmpty()) {
            throw new ProductCreateException("Sorry, error occurred while saving the images attached to the product. Please try again");
        }
        if (CollectionUtils.isNotEmpty(additionalImages)) {
            for (Integer image : additionalImages) {
                if (imageProductRepo.findById(image).isEmpty()) {
                    throw new ProductCreateException("Sorry, error occurred while saving the images attached to the product. Please try again");
                }
            }
        }

        product.setMainImage(mainImage);
        Product savedProduct = productsRepo.save(product);


        Optional<ImageProduct> temp = imageProductRepo.findById(mainImage);
        if (temp.isPresent()) {
            temp.get().setIdProduct(savedProduct.getId());
            imageProductRepo.save(temp.get());
        }



        if (CollectionUtils.isNotEmpty(additionalImages)) {
            for (Integer image : additionalImages) {
                temp = imageProductRepo.findById(image);
                if (temp.isPresent()) {
                    temp.get().setIdProduct(savedProduct.getId());
                    imageProductRepo.save(temp.get());
                }
            }
        }

        return savedProduct;
    }
}


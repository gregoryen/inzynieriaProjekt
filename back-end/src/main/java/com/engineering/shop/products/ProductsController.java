package com.engineering.shop.products;

import com.engineering.shop.categories.CategoriesController;
import com.engineering.shop.imageProducts.ImageProduct;
import com.engineering.shop.imageProducts.ImageProductRepo;
import com.engineering.shop.products.exception.ProductCreateException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public Iterable<Product> getAllProducts() {
        Iterable<Product> products = productsRepo.findAll();
        products.forEach(product -> {
            product.add(ControllerLinkBuilder.linkTo(ProductsController.class).slash(product.getId()).withSelfRel());
            product.getCategories().forEach(category -> {
                if (!category.hasLink("self"))
                    category.add(ControllerLinkBuilder.linkTo(CategoriesController.class).slash(category.getId()).withSelfRel());
            });
        });
        return products;
    }

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

        Optional<ImageProduct> temp = imageProductRepo.findById(mainImage);
        if (temp.isPresent()) {
            temp.get().setIdProduct(product.getId());
            imageProductRepo.save(temp.get());
        }


        product.setMainImage(mainImage);

        if (CollectionUtils.isNotEmpty(additionalImages)) {
            for (Integer image : additionalImages) {
                temp = imageProductRepo.findById(image);
                if (temp.isPresent()) {
                    temp.get().setIdProduct(product.getId());
                    imageProductRepo.save(temp.get());
                }
            }
        }

        return productsRepo.save(product);
    }
}


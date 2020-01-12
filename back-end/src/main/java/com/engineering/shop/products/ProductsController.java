package com.engineering.shop.products;

import com.engineering.shop.imageProducts.ImageProduct;
import com.engineering.shop.imageProducts.ImageProductRepo;
import com.engineering.shop.products.exception.ProductCreateException;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {

    private ProductPOJOToProductTransformer productPOJOToProductTransformer;
    private ProductsRepo productsRepo;
    private ImageProductRepo imageProductRepo;
    private ProductImageHolderValidator productImageHolderValidator;

    @Autowired
    public ProductsController(ProductPOJOToProductTransformer productPOJOToProductTransformer, ProductsRepo productsRepo, ImageProductRepo imageProductRepo, ProductImageHolderValidator productImageHolderValidator) {
        this.productPOJOToProductTransformer = productPOJOToProductTransformer;
        this.productsRepo = productsRepo;
        this.imageProductRepo = imageProductRepo;
        this.productImageHolderValidator = productImageHolderValidator;
    }

    @CrossOrigin
    @PostMapping
    public Product addProduct(@RequestBody @Validated ProductImageHolder productImageHolder) {
        Product product = productPOJOToProductTransformer.transform(productImageHolder.getProduct());
        Integer mainImage = product.getMainImage();
        List<Integer> additionalImages = productImageHolder.getAdditionalImages();

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getCode();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(productImageHolderValidator);
    }
}


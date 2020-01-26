package com.engineering.shop.products;

import com.engineering.shop.categories.CategoriesProductsController;
import com.engineering.shop.fileUpload.FileController;
import com.engineering.shop.imageProducts.ImageProduct;
import com.engineering.shop.imageProducts.ImageProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;


@Configuration
public class ProductResourceProcessor {
    ImageProductRepo imageProductRepo;

    @Autowired
    public ProductResourceProcessor(ImageProductRepo imageProductRepo) {
        this.imageProductRepo = imageProductRepo;
    }

    @Bean
    public RepresentationModelProcessor<EntityModel<Product>> productProcessor() {

        return new RepresentationModelProcessor<EntityModel<Product>>() {
            @Override
            public EntityModel<Product> process(EntityModel<Product> resource) {
                try {
                    resource.add(WebMvcLinkBuilder.linkTo(CategoriesProductsController.class.getMethod("getBranchByProductId", int.class),
                            Objects.requireNonNull(resource.getContent()).getId()).withRel("branch"));

                    HttpServletRequest request =
                            ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                                    .getRequest();

                    resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FileController.class).
                            downloadMainImage(Objects.requireNonNull(resource.getContent()).getId(), request)).withRel("mainImage"));

                    for (ImageProduct imageProduct :
                            imageProductRepo.findAllByIdProduct(Objects.requireNonNull(resource.getContent()).getId())) {
                        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FileController.class).
                                downloadAdditionalImage(imageProduct.getId(), request)).withRel("additionalImages"));
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
                    return resource;
            }
        };

    }
}

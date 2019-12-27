package com.engineering.shop.products;

import com.engineering.shop.categories.CategoriesController;
import com.engineering.shop.categories.CategoriesProductsController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.lang.reflect.Method;
import java.util.Objects;

@Configuration
public class Processor {
    @Bean
    public RepresentationModelProcessor<EntityModel<Product>> productProcessor() {

        return new RepresentationModelProcessor<EntityModel<Product>>() {

            @Override
            public EntityModel<Product> process(EntityModel<Product> resource) {
                try {
                    resource.add(WebMvcLinkBuilder.linkTo(CategoriesProductsController.class,CategoriesProductsController.class.getMethod("getBranchByProductId",int.class),
                            Objects.requireNonNull(resource.getContent()).getId()).withRel("branch"));
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

                return resource;
            }
        };
    }
}

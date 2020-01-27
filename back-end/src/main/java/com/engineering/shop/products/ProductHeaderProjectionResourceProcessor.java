package com.engineering.shop.products;

import com.engineering.shop.fileUpload.FileController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Configuration
public class ProductHeaderProjectionResourceProcessor  implements RepresentationModelProcessor<EntityModel<ProductHeaderProjection>> {
    public static final String CANCEL_REL = "cancel";


    private EntityLinks entityLinks;

    @Autowired
    public ProductHeaderProjectionResourceProcessor(EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    @Override
    public EntityModel<ProductHeaderProjection> process(EntityModel<ProductHeaderProjection> resource) {

        ProductHeaderProjection userProjection = resource.getContent();
        HttpServletRequest request =
                ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()))
                        .getRequest();

        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(FileController.class).
                downloadMainImage(Objects.requireNonNull(resource.getContent()).getId(), request)).withRel("mainImage"));
        return resource;
    }
}

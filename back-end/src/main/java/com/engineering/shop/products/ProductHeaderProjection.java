package com.engineering.shop.products;

import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(name = "header", types = {Product.class})
public interface ProductHeaderProjection {
    String getName();

    BigDecimal getPrice();

    Integer getMainImage();
}

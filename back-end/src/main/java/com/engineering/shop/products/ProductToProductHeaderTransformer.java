package com.engineering.shop.products;

import com.engineering.shop.common.Transformer;
import org.springframework.stereotype.Service;

@Service
public class ProductToProductHeaderTransformer implements Transformer<Product, ProductHeader> {
    @Override
    public ProductHeader transform(Product product) {
        return ProductHeader.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
}

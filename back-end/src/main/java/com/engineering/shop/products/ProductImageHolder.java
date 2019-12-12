package com.engineering.shop.products;

import lombok.Data;

import java.util.List;

@Data
class ProductImageHolder {
    private Product product;
    private Integer mainImage;
    private List<Integer> additionalImages;
}

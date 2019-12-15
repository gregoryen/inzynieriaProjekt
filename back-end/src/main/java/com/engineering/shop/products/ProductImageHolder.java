package com.engineering.shop.products;

import lombok.Data;

import java.util.List;

@Data
class ProductImageHolder {
    private ProductPOJO product;
    private List<Integer> additionalImages;
}

package com.engineering.shop.products;

import com.engineering.shop.imageProducts.ImageProduct;
import lombok.Data;

import java.util.List;

@Data
public class ProductImageHolder {
    private Product product;
    private Integer mainImage;
    private List<Integer> additionalImages;
}

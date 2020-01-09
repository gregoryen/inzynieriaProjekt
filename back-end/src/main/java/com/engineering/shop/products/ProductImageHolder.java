package com.engineering.shop.products;

import lombok.Data;

import java.util.List;

@Data
class ProductImageHolder {
    private ProductPOJO product;
    private List<Integer> additionalImages;
	public ProductPOJO getProduct() {
		// TODO Auto-generated method stub
		return null;
	}
	public List<Integer> getAdditionalImages() {
		// TODO Auto-generated method stub
		return null;
	}
}

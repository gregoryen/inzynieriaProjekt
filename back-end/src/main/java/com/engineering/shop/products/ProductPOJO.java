package com.engineering.shop.products;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@RequiredArgsConstructor
public class ProductPOJO {
    private Set<Integer> categories;
    private String name;
    private String description;
    private Integer mainCategoryId;
    private BigDecimal price;
    private String reference;
    private String isbn;
    private String ean13;
    private boolean active;
    private Integer mainImage;
	public Set<Integer> getCategories() {
		return categories;
	}
	public void setCategories(Set<Integer> categories) {
		this.categories = categories;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getMainCategoryId() {
		return mainCategoryId;
	}
	public void setMainCategoryId(Integer mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getReference() {
		return reference;
	}
	public void setReference(String reference) {
		this.reference = reference;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getEan13() {
		return ean13;
	}
	public void setEan13(String ean13) {
		this.ean13 = ean13;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Integer getMainImage() {
		return mainImage;
	}
	public void setMainImage(Integer mainImage) {
		this.mainImage = mainImage;
	}
    
    
}

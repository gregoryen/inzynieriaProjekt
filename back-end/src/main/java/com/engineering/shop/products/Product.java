package com.engineering.shop.products;

import com.engineering.shop.categories.Category;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;


@Entity
@Builder(access = AccessLevel.PUBLIC)
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Product extends RepresentationModel<Product> {
    @Singular
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "CATEGORIES_PRODUCTS",
            joinColumns = {@JoinColumn(name = "product_id")},
            inverseJoinColumns = {@JoinColumn(name = "category_id")})
    private Set<Category> categories;
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    private String name;
    private String description;
    private Integer mainCategoryId;
    private BigDecimal price;
    private String reference;
    private String isbn;
    private String ean13;
    private boolean active;
    private Integer mainImage;
	public Integer getMainCategoryId() {
		// TODO Auto-generated method stub
		return null;
	}
	public Set<Category> getCategories() {
		return categories;
	}
	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public void setMainCategoryId(Integer mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}
	public static Product builder() {
		// TODO Auto-generated method stub
		return null;
	}
	public Product name(String name2) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}

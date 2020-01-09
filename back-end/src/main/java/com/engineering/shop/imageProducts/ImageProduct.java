package com.engineering.shop.imageProducts;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "Imageproduct")
public class ImageProduct {
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Integer id;
    private Integer idProduct;
    private String extension;

    public ImageProduct() {
        this.idProduct = -1;
    }

    public ImageProduct(String extension) {
        this();
        this.extension = extension;
    }

    public String getImageName() {
        return this.id.toString() + "." + this.extension;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(Integer idProduct) {
		this.idProduct = idProduct;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}
    
    
}

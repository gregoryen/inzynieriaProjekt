package com.engineering.shop.warehouse.models;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class StockAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stockAmountId;

//    @OneToOne(cascade = CascadeType.ALL))
    @NotNull
    private Integer productId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @NotNull
    private Measure measure;

    private Double amount;

    @NotNull
    private Boolean available;

    public StockAmount() {
    }

    public StockAmount(Integer productId, Measure measure, Double amount, Boolean available) {
        this.productId = productId;
        this.measure = measure;
        this.amount = amount;
        this.available = available;
    }

    public Boolean isAvailable() {
        return available;
    }

	public Integer getProductId() {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer getStockAmountId() {
		return stockAmountId;
	}

	public void setStockAmountId(Integer stockAmountId) {
		this.stockAmountId = stockAmountId;
	}

	public Measure getMeasure() {
		return measure;
	}

	public void setMeasure(Measure measure) {
		this.measure = measure;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	
	
}
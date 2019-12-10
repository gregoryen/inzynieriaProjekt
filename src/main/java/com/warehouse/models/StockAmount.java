package com.warehouse.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class StockAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stockAmountId;

    @NotNull
    private Integer productId;

    @NotNull
    private Integer categoryId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @NotNull
    private Measure measure;

    @NotNull
    private Double amount;

    @NotNull
    private Boolean available;

    public StockAmount() {
    }

    public StockAmount(Integer productId, Integer categoryId, Measure measure, Double amount, Boolean available) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.measure = measure;
        this.amount = amount;
        this.available = available;
    }

    public Integer getStockAmountId() {
        return stockAmountId;
    }

    public void setStockAmountId(Integer stockAmountId) {
        this.stockAmountId = stockAmountId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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
}
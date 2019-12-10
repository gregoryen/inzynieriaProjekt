package com.warehouse.models;

import javax.persistence.*;

@Entity
public class StockAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer stockAmountId;

    private Integer productId;

    private Integer categoryId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Measure measure;

    private Double amount;

    public StockAmount() {
    }

    public StockAmount(Integer productId, Integer categoryId, Measure measure, Double amount) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.measure = measure;
        this.amount = amount;
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
}
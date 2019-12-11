package com.warehouse.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

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

    @ManyToMany
    @NotNull
    private List<Supply> supplies;

    @ManyToMany
    @NotNull
    private List<StockAmountChange> stockAmountChanges;

    public StockAmount() {
    }

    public StockAmount(Integer productId, Integer categoryId, Measure measure, Double amount, Boolean available, List<Supply> supplies, List<StockAmountChange> stockAmountChanges) {
        this.productId = productId;
        this.categoryId = categoryId;
        this.measure = measure;
        this.amount = amount;
        this.available = available;
        this.supplies = supplies;
        this.stockAmountChanges = stockAmountChanges;
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

    public List<Supply> getSupplies() {
        return supplies;
    }

    public void setSupplies(List<Supply> supplies) {
        this.supplies = supplies;
    }

    public List<StockAmountChange> getStockAmountChanges() {
        return stockAmountChanges;
    }

    public void setStockAmountChanges(List<StockAmountChange> stockAmountChanges) {
        this.stockAmountChanges = stockAmountChanges;
    }
}
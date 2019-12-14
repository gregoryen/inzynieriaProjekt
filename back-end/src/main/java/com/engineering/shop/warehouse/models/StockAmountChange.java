package com.engineering.shop.warehouse.models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class StockAmountChange {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stockAmountChangeId;

    @NotNull
    private Double previousAmount;

    @NotNull
    private Double currentAmount;

    @NotNull
    private LocalDateTime changeDateTime;

    @NotNull
    private String changeInfo;

    @OneToOne
    @NotNull
    private StockAmount stockAmount;

    public StockAmountChange() {
    }

    public StockAmountChange(Integer stockAmountChangeId, Double previousAmount, Double currentAmount, LocalDateTime changeDateTime, String changeInfo, StockAmount stockAmount) {
        this.stockAmountChangeId = stockAmountChangeId;
        this.previousAmount = previousAmount;
        this.currentAmount = currentAmount;
        this.changeDateTime = changeDateTime;
        this.changeInfo = changeInfo;
        this.stockAmount = stockAmount;
    }

    public Integer getStockAmountChangeId() {
        return stockAmountChangeId;
    }

    public void setStockAmountChangeId(Integer stockAmountChangeId) {
        this.stockAmountChangeId = stockAmountChangeId;
    }

    public Double getPreviousAmount() {
        return previousAmount;
    }

    public void setPreviousAmount(Double previousAmount) {
        this.previousAmount = previousAmount;
    }

    public Double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public LocalDateTime getChangeDateTime() {
        return changeDateTime;
    }

    public void setChangeDateTime(LocalDateTime changeDateTime) {
        this.changeDateTime = changeDateTime;
    }

    public String getChangeInfo() {
        return changeInfo;
    }

    public void setChangeInfo(String changeInfo) {
        this.changeInfo = changeInfo;
    }

    public StockAmount getStockAmount() {
        return stockAmount;
    }

    public void setStockAmount(StockAmount stockAmount) {
        this.stockAmount = stockAmount;
    }
}
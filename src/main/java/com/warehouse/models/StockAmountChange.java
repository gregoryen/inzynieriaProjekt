package com.warehouse.models;


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
    private Double amountChange;

    @NotNull
    private LocalDateTime changeDateTime;

    @NotNull
    private String changed;

    @ManyToMany
    @NotNull
    private List<StockAmount> stockAmounts;

    @ManyToMany
    @NotNull
    private List<Report> reports;

    public StockAmountChange() {
    }

    public StockAmountChange(Double amountChange, LocalDateTime changeDateTime, String changed, List<StockAmount> stockAmounts, List<Report> reports) {
        this.amountChange = amountChange;
        this.changeDateTime = changeDateTime;
        this.changed = changed;
        this.stockAmounts = stockAmounts;
        this.reports = reports;
    }

    public Integer getStockAmountChangeId() {
        return stockAmountChangeId;
    }

    public void setStockAmountChangeId(Integer stockAmountChangeId) {
        this.stockAmountChangeId = stockAmountChangeId;
    }

    public Double getAmountChange() {
        return amountChange;
    }

    public void setAmountChange(Double amountChange) {
        this.amountChange = amountChange;
    }

    public LocalDateTime getChangeDateTime() {
        return changeDateTime;
    }

    public void setChangeDateTime(LocalDateTime changeDateTime) {
        this.changeDateTime = changeDateTime;
    }

    public String getChanged() {
        return changed;
    }

    public void setChanged(String changed) {
        this.changed = changed;
    }

    public List<StockAmount> getStockAmounts() {
        return stockAmounts;
    }

    public void setStockAmounts(List<StockAmount> stockAmounts) {
        this.stockAmounts = stockAmounts;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
}
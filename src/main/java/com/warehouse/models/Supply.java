package com.warehouse.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplyId;

    @OneToOne
    @NotNull
    private Supplier supplier;

    @JoinTable
    @OneToMany
    @NotNull
    private List<StockAmount> stockAmounts;

    @NotNull
    private LocalDateTime deliveryDate;

    public Supply()  {
    }

    public Supply(Supplier supplier, List<StockAmount> stockAmounts, LocalDateTime deliveryDate) {
        this.supplier = supplier;
        this.stockAmounts = stockAmounts;
        this.deliveryDate = deliveryDate;
    }

    public Integer getSupplyId() {
        return supplyId;
    }

    public void setSupplyId(Integer supplyId) {
        this.supplyId = supplyId;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public List<StockAmount> getStockAmounts() {
        return stockAmounts;
    }

    public void setStockAmounts(List<StockAmount> stockAmounts) {
        this.stockAmounts = stockAmounts;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }
}
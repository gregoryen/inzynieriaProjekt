package com.engineering.shop.warehouse.models;

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

    @NotNull
    private LocalDateTime deliveryDateTime;

    @ManyToMany
    @NotNull
    private List<StockAmount> stockAmounts;

    public Supply()  {
    }

    public Supply(Supplier supplier, LocalDateTime deliveryDateTime, List<StockAmount> stockAmounts) {
        this.supplier = supplier;
        this.deliveryDateTime = deliveryDateTime;
        this.stockAmounts = stockAmounts;
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

//    public List<StockAmount> getStockAmounts() {
//        return stockAmounts;
//    }
//
//    public void setStockAmounts(List<StockAmount> stockAmounts) {
//        this.stockAmounts = stockAmounts;
//    }

    public LocalDateTime getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(LocalDateTime deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }
}
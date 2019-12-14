package com.engineering.shop.warehouse.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
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

    @OneToOne(cascade = CascadeType.ALL)
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
}
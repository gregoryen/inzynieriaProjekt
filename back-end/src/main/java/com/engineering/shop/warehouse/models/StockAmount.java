package com.engineering.shop.warehouse.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class StockAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stockAmountId;

    @NotNull
    private Integer productId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @NotNull
    private Measure measure;

    private Double amount;

    @NotNull
    private Boolean available;

    @NotNull
    private LocalDateTime dateTime;

    public StockAmount() {
    }

    public StockAmount(Integer productId, Measure measure, Double amount, Boolean available, LocalDateTime dateTime) {
        this.productId = productId;
        this.measure = measure;
        this.amount = amount;
        this.available = available;
        this.dateTime = dateTime;
    }

    public Boolean isAvailable() {
        return available;
    }
}
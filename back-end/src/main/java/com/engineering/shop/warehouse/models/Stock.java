package com.engineering.shop.warehouse.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stockId;

    @NotNull
    private Integer productId;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @NotNull
    private Measure measure;

    @NotNull
    private Double amount;

    @NotNull
    private Boolean available;

    @NotNull
    private LocalDateTime update;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @NotNull
    private Status status;

    private Integer supplierId;

    public Stock() {
    }

    public Stock(
            Integer stockId,
            Integer productId,
            Measure measure,
            Double amount,
            Boolean available,
            LocalDateTime update,
            Status status,
            Integer supplierId
    ) {
        this.productId = productId;
        this.measure = measure;
        this.amount = amount;
        this.available = available;
        this.update = update;
        this.status = status;
        this.supplierId = supplierId;
    }
}
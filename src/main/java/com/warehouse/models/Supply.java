package com.warehouse.models;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer supplyId;

    @OneToOne
    private Supplier supplier;

    @JoinTable
    @OneToMany
    private List<StockAmount> stockAmounts;

    private LocalDateTime deliveryDate;

    public Supply()  {
    }

    public Supply(Supplier supplier, List<StockAmount> stockAmounts, LocalDateTime deliveryDate) {
        this.supplier = supplier;
        this.stockAmounts = stockAmounts;
        this.deliveryDate = deliveryDate;
    }
}

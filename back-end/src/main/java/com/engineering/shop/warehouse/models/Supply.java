package com.engineering.shop.warehouse.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Supply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer supplyId;

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    private Supplier supplier;

    @NotNull
    private LocalDateTime deliveryDateTime;

    @ManyToMany(cascade = CascadeType.ALL)
    @NotNull
    private List<StockAmount> stockAmounts;

    public Supply()  {
    }

    public Supply(Integer supplyId, Supplier supplier, LocalDateTime deliveryDateTime, List<StockAmount> stockAmounts) {
        this.supplyId = supplyId;
        this.supplier = supplier;
        this.deliveryDateTime = deliveryDateTime;
        this.stockAmounts = stockAmounts;
    }
}
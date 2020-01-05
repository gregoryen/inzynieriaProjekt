//package com.engineering.shop.warehouse.models;
//
//import lombok.Getter;
//import lombok.Setter;
//
//import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Getter
//@Setter
//@Entity
//public class Report {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer reportId;
//
//    @NotNull
//    private LocalDateTime creationDateTime;
//
//    @NotNull
//    private LocalDateTime startDateTime;
//
//    @NotNull
//    private LocalDateTime endDateTime;
//
//    @ManyToMany(cascade = CascadeType.ALL)
//    @NotNull
//    private List<StockAmountChange> stockAmountChanges;
//
//    public Report() {
//    }
//
//    public Report(Integer reportId, LocalDateTime creationDateTime, LocalDateTime startDateTime, LocalDateTime endDateTime, List<StockAmountChange> stockAmountChanges) {
//        this.reportId = reportId;
//        this.creationDateTime = creationDateTime;
//        this.startDateTime = startDateTime;
//        this.endDateTime = endDateTime;
//        this.stockAmountChanges = stockAmountChanges;
//    }
//}

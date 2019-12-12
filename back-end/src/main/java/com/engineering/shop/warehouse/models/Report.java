package com.warehouse.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reportId;

    @NotNull
    private LocalDateTime creationDateTime;

    @NotNull
    private LocalDateTime startDateTime;

    @NotNull
    private LocalDateTime endDateTime;

    @ManyToMany
    @NotNull
    private List<StockAmountChange> stockAmountChanges;

    public Report() {
    }

    public Report(Integer reportId, LocalDateTime creationDateTime, LocalDateTime startDateTime, LocalDateTime endDateTime, List<StockAmountChange> stockAmountChanges) {
        this.reportId = reportId;
        this.creationDateTime = creationDateTime;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.stockAmountChanges = stockAmountChanges;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public LocalDateTime getCreationDateTime() {
        return creationDateTime;
    }

    public void setCreationDateTime(LocalDateTime creationDateTime) {
        this.creationDateTime = creationDateTime;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public List<StockAmountChange> getStockAmountChanges() {
        return stockAmountChanges;
    }

    public void setStockAmountChanges(List<StockAmountChange> stockAmountChanges) {
        this.stockAmountChanges = stockAmountChanges;
    }
}

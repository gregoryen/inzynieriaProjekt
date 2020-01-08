package com.engineering.shop.warehouse.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
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

    public Report() {
    }

    public Report(Integer reportId, LocalDateTime creationDateTime,
                  LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.reportId = reportId;
        this.creationDateTime = creationDateTime;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }
}

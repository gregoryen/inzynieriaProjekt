package com.engineering.shop.warehouse.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    @NotNull
    @ElementCollection(targetClass=String.class)
    private List<String> changes;

    public Report() {
    }

    public Report(Integer reportId, LocalDateTime creationDateTime,
                  LocalDateTime startDateTime, LocalDateTime endDateTime,
                  List<String> changes) {
        this.reportId = reportId;
        this.creationDateTime = creationDateTime;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.changes = changes;
    }
}
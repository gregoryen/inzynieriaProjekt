package com.warehouse.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehouse.models.Report;
import com.warehouse.models.StockAmountChange;
import com.warehouse.repositories.ReportRepository;
import com.warehouse.repositories.StockAmountChangeRepository;
import com.warehouse.repositories.StockAmountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/reports")
public class ReportController {

    ReportRepository reportRepository;
    StockAmountChangeRepository stockAmountChangeRepository;

    @Autowired
    public ReportController(ReportRepository reportRepository, StockAmountChangeRepository stockAmountChangeRepository) {
        this.reportRepository = reportRepository;
        this.stockAmountChangeRepository = stockAmountChangeRepository;
    }

    @GetMapping("/all")
    public Iterable<StockAmountChange> getAllStockAmountChanges() {
        return stockAmountChangeRepository.findAll();
    }

    @PostMapping("/create")
    public String create(@RequestBody Map<String, String> dateTimes) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(dateTimes.get("startDateTime"), formatter);
        LocalDateTime end = LocalDateTime.parse(dateTimes.get("endDateTime"), formatter);
        Iterable<StockAmountChange> stockAmountChanges = stockAmountChangeRepository.findAllByChangeDateTimeBetween(start, end);
        List<StockAmountChange> stockAmountChangesList = new ArrayList<StockAmountChange>();
        for (StockAmountChange stockAmountChange : stockAmountChanges) {
            stockAmountChangesList.add(stockAmountChange);
        }
        Report report = new Report();
        report.setCreationDateTime(LocalDateTime.now());
        report.setStartDateTime(start);
        report.setEndDateTime(end);
        report.setStockAmountChanges(stockAmountChangesList);
        reportRepository.save(report);
        return "Created";
    }

    @PostMapping("/export")
    public String exportToCSVFileFromDatabase(@RequestParam("filepath") String filepath,
                                              @RequestParam("filepath") String startDateTime,
                                              @RequestParam("filepath") String endDateTime) {
        // findall
        return "Exported";
    }

    @PostMapping("/import")
    public String importToDatabaseFromCSVFile(@RequestBody Report report) {
        return "Imported";
    }
}
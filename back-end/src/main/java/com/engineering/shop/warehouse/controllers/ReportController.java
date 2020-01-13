package com.engineering.shop.warehouse.controllers;

import com.engineering.shop.products.Product;
import com.engineering.shop.products.ProductsRepo;
import com.engineering.shop.warehouse.models.Report;
import com.engineering.shop.warehouse.models.StockAmount;
import com.engineering.shop.warehouse.repositories.ReportRepository;
import com.engineering.shop.warehouse.repositories.StockAmountRepository;
import org.apache.commons.io.input.ObservableInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/reports")
public class ReportController {

    ReportRepository reportRepository;
    StockAmountRepository stockAmountRepository;
    ProductsRepo productsRepo;

    @Autowired
    public ReportController(ReportRepository reportRepository,
                            StockAmountRepository stockAmountRepository,
                            ProductsRepo productsRepo) {
        this.reportRepository = reportRepository;
        this.stockAmountRepository = stockAmountRepository;
        this.productsRepo = productsRepo;
    }

    @GetMapping("/all")
    public Iterable<Report> getReports() {
        return reportRepository.findAll();
    }

    @PostMapping("/create")
    public Map<String, String> create(@RequestParam String startDateTime, @RequestParam String endDateTime) {
        Map<String, String> result = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-ddHH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startDateTime, formatter);
        LocalDateTime end = LocalDateTime.parse(endDateTime, formatter);
        Iterable<StockAmount> stockAmounts = stockAmountRepository.findAllByDateTimeBetween(start, end);

        String info = "";
        List<String> changes = new ArrayList<>();
        for (StockAmount s : stockAmounts) {
            info = "Produkt: " + productsRepo.findById(s.getProductId()).get().getName() + ", " +
                    s.getAmount() + " " + s.getMeasure() + ", " +
                    "aktualizacja: " + s.getDateTime().toString().replace("T", " ").substring(0, 16);
            changes.add(info);
        }

        if (changes.size() != 0) {
            Report report = new Report();
            report.setCreationDateTime(LocalDateTime.now());
            report.setStartDateTime(start);
            report.setEndDateTime(end);
            report.setChanges(changes);

            reportRepository.save(report);

            DateTimeFormatter f = DateTimeFormatter.ISO_DATE_TIME;

            result.put("status", "created");
            result.put("reportId", Integer.toString(report.getReportId()));
            result.put("creationDateTime", report.getCreationDateTime().format(f));
            result.put("startDateTime", report.getStartDateTime().format(f));
            result.put("endDateTime", report.getEndDateTime().format(f));
            String changesAsString = "";
            for (int i = 0; i < changes.size(); i++) {
                changesAsString += changes.get(i) + (i == changes.size() - 1 ? "" : ";");
            }
            result.put("changes", changesAsString);
        } else {
            result.put("status", "failed");
        }
        return result;
    }
}
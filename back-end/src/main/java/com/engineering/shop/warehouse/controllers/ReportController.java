package com.engineering.shop.warehouse.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warehouse.models.Report;
import com.warehouse.models.StockAmountChange;
import com.warehouse.repositories.ReportRepository;
import com.warehouse.repositories.StockAmountChangeRepository;
import com.warehouse.repositories.StockAmountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
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
        String returnStatement;
        if (stockAmountChangesList != null) {
            Report report = new Report();
            report.setCreationDateTime(LocalDateTime.now());
            report.setStartDateTime(start);
            report.setEndDateTime(end);
            report.setStockAmountChanges(stockAmountChangesList);
            reportRepository.save(report);
            returnStatement = "Report created";
        } else {
            // jakis wyjatek czy HTTP blad
            returnStatement = "There are not any stock amount changes to include in report.";
        }
        return returnStatement;
    }

    @PostMapping("/export")
    public String exportToCSVFileFromDatabase(@RequestBody Map<String, String> filepathAndReportId) {
        try {
            String filepath = filepathAndReportId.get("filepath");
            Integer reportId = Integer.parseInt(filepathAndReportId.get("reportId"));

            Report report = reportRepository.findByReportId(reportId).
                    orElseThrow(() -> new ResourceNotFoundException("Report not found with reportId"));
            List<StockAmountChange> stockAmountChanges = report.getStockAmountChanges();

            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filepath), StandardCharsets.ISO_8859_1));
            String separator = ",";
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("\"Id zmiany\",\"Poprzednia ilosc\",\"Aktualna ilosc\",\"Data zmiany\",\"Informacja o zmianie\",\"Id produktu\",\"Id kategorii\"");
            bufferedWriter.write(stringBuffer.toString());
            bufferedWriter.newLine();
            for (StockAmountChange stockAmountChange : stockAmountChanges) {
                stringBuffer = new StringBuffer();
                stringBuffer.append("\""+stockAmountChange.getStockAmountChangeId()+"\"");
                stringBuffer.append(separator);
                stringBuffer.append("\""+stockAmountChange.getPreviousAmount()+"\"");
                stringBuffer.append(separator);
                stringBuffer.append("\""+stockAmountChange.getCurrentAmount()+"\"");
                stringBuffer.append(separator);
                stringBuffer.append("\""+stockAmountChange.getChangeDateTime()+"\"");
                stringBuffer.append(separator);
                String changeInfo = stockAmountChange.getChangeInfo();
                if (changeInfo.equals("SOLD_OUT")) {
                    changeInfo = "wyprzedano";
                } else if (changeInfo.equals("PROVIDED")) {
                    changeInfo = "dostarczono";
                } else if (changeInfo.equals("SOLD")) {
                    changeInfo = "sprzedano";
                }
                stringBuffer.append("\""+changeInfo+"\"");
                stringBuffer.append(separator);
                stringBuffer.append("\""+stockAmountChange.getStockAmount().getProductId()+"\"");
                stringBuffer.append(separator);
                stringBuffer.append("\""+stockAmountChange.getStockAmount().getCategoryId()+"\"");
                bufferedWriter.write(stringBuffer.toString());
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "Exported";
    }

    @PostMapping("/import")
    public String importToDatabaseFromCSVFile(@RequestBody Report report) {
        return "Imported";
    }
}
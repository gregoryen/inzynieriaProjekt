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

//    @GetMapping("/readable")
//    public Map<String, List<String>> getReadableReports() {
//        Map<String, List<String>> result = new HashMap<>();
//
//        Iterable<Report> reports = reportRepository.findAll();
//        for (Report r : reports) {
//            List<Integer> ids = toInts(r.getInfo().split(";"));
//            List<StockAmount> selected = stockAmountRepository.findAllByStockAmountIdIn(ids);
//            List<String> lines = getLines(selected);
//            result.put(Integer.toString(r.getReportId()), lines);
//        }
//
//        return result;
//    }
//
//    private List<Integer> toInts(String[] strings) {
//        List<Integer> ints = new ArrayList<>();
//        for (String s : strings) {
//            ints.add(Integer.parseInt(s));
//        }
//        return ints;
//    }
//
//    private List<String> getLines(List<StockAmount> selected) {
//        List<String> result = new ArrayList<>();
//
//        List<Integer> productsIds = getProductsIds(selected);
//        Map<Integer, String> products = getProductsMap(productsIds);
//
//        for (StockAmount s : selected) {
//            result.add(
//                    "Produkt: " + products.get(s.getProductId()) + ", " +
//                    "ilosc: " + s.getAmount() + ", " +
//                    "data dodania/aktualizacji: " + s.getDateTime() + ", " +
//                    "dostepny: " + (s.getAvailable() ? "tak" : "nie") + ", " +
//                    "jednostka: " + (s.getMeasure()));
//        }
//
//        return result;
//    }
//
//    private List<Integer> getProductsIds(List<StockAmount> selected) {
//        Set<Integer> set = new HashSet<>(selected.size());
//        return (ArrayList) selected.stream().filter(s -> set.add(s.getProductId())).collect(Collectors.toList());
//    }
//
//    private Map<Integer, String> getProductsMap(List<Integer> productsIds) {
//        Map<Integer, String> products = new HashMap<>();
//        for (Integer id : productsIds) {
//            int ID = id;
//            String name = productsRepo.findById(ID).get().getName();
//            products.put(id, name);
//        }
//        return products;
//    }

//    private List<StockAmount> getAllByStockIds(Iterable<StockAmount> stocks, Integer[] ids) {
//        List<StockAmount> result = new ArrayList<>();
//        for (int i = 0; i < ids.length; i++) {
//            result.add(stockAmountRepository.findStockAmountByStockAmountId(ids[i]).get());
//        }
//        return result;
//    }

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
//            for (String s : changes) {
//                changesAsString += s + ";";
//            }
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
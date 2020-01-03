package com.engineering.shop.warehouse.controllers;

import com.engineering.shop.warehouse.models.StockAmountChange;
import com.engineering.shop.warehouse.repositories.StockAmountChangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(path = "/stock_amount_changes")
public class StockAmountChangeController {

    StockAmountChangeRepository stockAmountChangeRepository;

    @Autowired
    public StockAmountChangeController(StockAmountChangeRepository stockAmountChangeRepository) {
        this.stockAmountChangeRepository = stockAmountChangeRepository;
    }

    // wlasciwy format: http://localhost:8080/stock_amount_changes/all_between_times?startDateTime=2019-12-11%2019:15:16&endDateTime=2019-12-11%2019:15:30
    @GetMapping("/all_between_times")
    public Iterable<StockAmountChange> getAllByChangeDateTimeBetween(@RequestParam String startDateTime, @RequestParam String endDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime start = LocalDateTime.parse(startDateTime, formatter);
        LocalDateTime end = LocalDateTime.parse(endDateTime, formatter);
        return stockAmountChangeRepository.findAllByChangeDateTimeBetween(start, end);
    }
}
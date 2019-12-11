package com.warehouse.controllers;

import com.warehouse.repositories.StockAmountChangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/stock_amount_changes")
public class StockAmountChangeController {

    StockAmountChangeRepository stockAmountChangeRepository;

    @Autowired
    public StockAmountChangeController(StockAmountChangeRepository stockAmountChangeRepository) {
        this.stockAmountChangeRepository = stockAmountChangeRepository;
    }


}
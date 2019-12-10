package com.warehouse.controllers;

import com.warehouse.models.Measure;
import com.warehouse.models.StockAmount;
import com.warehouse.repositories.StockAmountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/stock_amounts")
public class StockAmountController {

    private StockAmountRepository stockAmountRepository;

    @Autowired
    public StockAmountController(StockAmountRepository stockAmountRepository) {
        this.stockAmountRepository = stockAmountRepository;
    }

    @GetMapping(path = "/all")
    public Iterable<StockAmount> getAll() {
        return stockAmountRepository.findAll();
    }

    @PostMapping(path = "/add")
    public @ResponseBody
    String addNewStockAmount(@RequestParam Integer productId, @RequestParam Integer categoryId,
                             @RequestParam Measure measure, @RequestParam Double amount) {
        StockAmount stockAmount = new StockAmount(productId, categoryId, measure, amount);
        stockAmountRepository.save(stockAmount);
        return "Saved";
    }
}

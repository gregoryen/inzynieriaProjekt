package com.warehouse.controllers;

import com.warehouse.models.StockAmount;
import com.warehouse.repositories.StockAmountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
//import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Optional;

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

    @GetMapping(path = "/{stockAmountId}")
    public Optional<StockAmount> getByStockAmountId(@PathVariable Integer stockAmountId) {
        return stockAmountRepository.findByStockAmountId(stockAmountId);
    }

    @GetMapping(path = "/availability/{available}")
    public Optional<StockAmount> getByAvailability(@PathVariable Boolean available) {
        return stockAmountRepository.findByAvailable(available);
    }

    @PostMapping(path = "/add")
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody
    String addStockAmount(@RequestBody StockAmount stockAmount) {
        stockAmountRepository.save(stockAmount);
        return "Saved";
    }

    @PostMapping(path = "/update")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public @ResponseBody
    String updateStockAmount(@RequestBody StockAmount stockAmount) {
        stockAmountRepository.save(stockAmount);
        return "Updated";
    }

//    @PatchMapping("/update/{stockAmountId}")
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public @ResponseBody
//    String updateStockAmount(@PathVariable Integer stockAmountId, @RequestBody Double amount) {
//        Optional<StockAmount> optionalStockAmount = stockAmountRepository.findByStockAmountId(stockAmountId);
//        StockAmount stockAmount = optionalStockAmount.orElseThrow(() -> new ResourceNotFoundException("StockAmount not found with stockAmountId"));
//        stockAmount.setAmount(amount);
//        stockAmountRepository.save(stockAmount);
//        return "Updated";
//    }
}
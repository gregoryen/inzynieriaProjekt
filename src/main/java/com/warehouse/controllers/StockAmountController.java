package com.warehouse.controllers;

import com.warehouse.models.StockAmount;
import com.warehouse.repositories.StockAmountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

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
    public Iterable<StockAmount> getAllByAvailability(@PathVariable Boolean available) {
        return stockAmountRepository.findAllByAvailable(available);
    }

    @PostMapping(path = "/add")
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody
    String addStockAmount(@RequestBody StockAmount stockAmount) {
        if (stockAmount.getAmount() > 0.0) {
            stockAmount.setAvailable(true);
        }
        stockAmountRepository.save(stockAmount);
        return "Saved";
    }

//    @PatchMapping("/update")
//    @ResponseStatus(value = HttpStatus.NO_CONTENT)
//    public @ResponseBody
//    String updateStockAmount(@RequestBody StockAmount newStockAmount) {
//        Integer stockAmountId = newStockAmount.getStockAmountId();
//        Optional<StockAmount> optionalStockAmount = stockAmountRepository.findByStockAmountId(stockAmountId);
//        StockAmount stockAmount = optionalStockAmount.orElseThrow(() -> new ResourceNotFoundException("StockAmount not found with stockAmountId"));
//        Double amount = newStockAmount.getAmount();
//        stockAmount.setAmount(amount);
//        if (stockAmount.getAmount() == 0.0 && stockAmount.isAvailable() == true) {
//            stockAmount.setAvailable(false);
//        } else if (stockAmount.getAmount() > 0.0 && stockAmount.isAvailable() == false) {
//            stockAmount.setAvailable(true);
//        }
//        stockAmountRepository.save(stockAmount);
//        return "Updated";
//    }

    @PatchMapping("/increase")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    String increaseAmount(@RequestBody StockAmount newStockAmount) {
        Integer stockAmountId = newStockAmount.getStockAmountId();
        StockAmount stockAmount  = stockAmountRepository.findByStockAmountId(stockAmountId).
                orElseThrow(() -> new ResourceNotFoundException("StockAmount not found with stockAmountId"));
        Double newAmount = newStockAmount.getAmount();
        Double oldAmount = stockAmount.getAmount();
        if (newAmount > 0.0) {
            stockAmount.setAmount(oldAmount + newAmount);
            if (!stockAmount.isAvailable()) {
                stockAmount.setAvailable(true);
            }
        } else {
            // kod bledu HTTP
        }
        stockAmountRepository.save(stockAmount);
        return "Increased";
    }

    @PatchMapping("/decrease")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    String decreaseAmount(@RequestBody StockAmount newStockAmount) {
        Integer stockAmountId = newStockAmount.getStockAmountId();
        StockAmount stockAmount  = stockAmountRepository.findByStockAmountId(stockAmountId).
                orElseThrow(() -> new ResourceNotFoundException("StockAmount not found with stockAmountId"));
        Double newAmount = newStockAmount.getAmount();
        Double oldAmount = stockAmount.getAmount();
        if (oldAmount - newAmount >= 0.0) {
            stockAmount.setAmount(oldAmount - newAmount);
            if (stockAmount.isAvailable() && stockAmount.getAmount() == 0.0) {
                stockAmount.setAvailable(false);
            }
        } else {
            // kod bledu HTTP
        }
        stockAmountRepository.save(stockAmount);
        return "Decreased";
    }
}
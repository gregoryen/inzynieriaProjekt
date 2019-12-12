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
        Iterable<StockAmount> stockAmounts = stockAmountRepository.findAll();
        if (stockAmounts == null) {
            throw new ResourceNotFoundException("There are not any stock amounts");
        }
        return stockAmounts;
    }

    @GetMapping(path = "/{stockAmountId}")
    public Optional<StockAmount> getByStockAmountId(@PathVariable Integer stockAmountId) {
        Optional<StockAmount> optionalStockAmount = stockAmountRepository.findStockAmountByStockAmountId(stockAmountId);
        if (optionalStockAmount == null) {
            throw new ResourceNotFoundException("Stock amount not found with provided stock amount id");
        }
        return optionalStockAmount;
    }

    @GetMapping(path = "/products/amount/{productId}")
    public Double getAmountByProductId(@PathVariable Integer productId) {
        StockAmount stockAmount = stockAmountRepository.findStockAmountByProductId(productId).
                orElseThrow(() -> new ResourceNotFoundException("Stock amount not found with provided product id"));
        return stockAmount.getAmount();
    }

    // "przed usunięciem produktu moglibyśmy jeszcze sprawdzić, czy jest on jeszcze na magazynie"
    @GetMapping(path = "/products/availability/{productId}")
    public Boolean isAvailableByProductId(@PathVariable Integer productId) {
        StockAmount stockAmount = stockAmountRepository.findStockAmountByProductId(productId).
                orElseThrow(() -> new ResourceNotFoundException("Stock amount not found with provided product id"));
        return stockAmount.isAvailable();
    }

    @GetMapping(path = "/availability/{available}")
    public Iterable<StockAmount> getAllByAvailability(@PathVariable Boolean available) {
        Iterable<StockAmount> stockAmounts = stockAmountRepository.findAllByAvailable(available);
        if (stockAmounts == null) {
            throw new ResourceNotFoundException(
                    available ? "There are not any available stock amounts" : "There are not any unavailable stock amounts");
        }
        return stockAmounts;
    }

    // TODO: potrzebne info, czy produkt w spisie modułu Produkty jest, czy nie ma,
    //  jeśli nie ma,
    //  to sami tworzymy stan magazynowy z iloscia produktu nowego
    //  w takim wypadku potrzebny endpoint do dodania nowego produktu w module Produkty
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

    // "metodę przyjmującą id produktu i na tej podstawie tworzył stan magazynowy"
    @PostMapping(path = "/add_empty")
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody
    String addEmptyStockAmount(@RequestBody StockAmount stockAmount) {
        if (!stockAmountRepository.existsStockAmountByProductId(stockAmount.getProductId())) {
            stockAmountRepository.save(stockAmount);
        } else {
            // http 400 cos tam
        }
        return "Saved";
    }

    @PatchMapping("/increase")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    String increaseAmount(@RequestBody StockAmount newStockAmount) {
        Integer stockAmountId = newStockAmount.getStockAmountId();
        StockAmount stockAmount  = stockAmountRepository.findStockAmountByStockAmountId(stockAmountId).
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
        StockAmount stockAmount  = stockAmountRepository.findStockAmountByStockAmountId(stockAmountId).
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
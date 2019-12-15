package com.engineering.shop.warehouse.controllers;

import com.engineering.shop.warehouse.models.Measure;
import com.engineering.shop.warehouse.models.Supplier;
import com.google.common.collect.Iterables;
import com.engineering.shop.warehouse.exceptions.UnprocessableEntityException;
import com.engineering.shop.warehouse.models.StockAmount;
import com.engineering.shop.warehouse.repositories.StockAmountRepository;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
        if (Iterables.size(stockAmounts) == 0) {
            throw new ResourceNotFoundException("There are not any stock amounts");
        }
        return stockAmounts;
    }

    @GetMapping(path = "/{stockAmountId}")
    public Optional<StockAmount> getByStockAmountId(@PathVariable Integer stockAmountId) {
        Optional<StockAmount> optionalStockAmount = stockAmountRepository.findStockAmountByStockAmountId(stockAmountId);
        optionalStockAmount.orElseThrow(() -> new ResourceNotFoundException("Stock amount not found with provided stock amount id"));
        return optionalStockAmount;
    }

    @GetMapping(path = "/products/amount/{productId}")
    public Double getAmountByProductId(@PathVariable Integer productId) {
        StockAmount stockAmount = stockAmountRepository.findStockAmountByProductId(productId).
                orElseThrow(() -> new ResourceNotFoundException("Stock amount not found with provided product id"));
        Double amount = stockAmount.getAmount();
        if (amount == null) {
            throw new ResourceNotFoundException("Amount of stock amount with provided product id equals null");
        }
        return amount;
    }

    @GetMapping(path = "/products/avail/{productId}")
    public Boolean isAvailableByProductId(@PathVariable Integer productId) {
        StockAmount stockAmount = stockAmountRepository.findStockAmountByProductId(productId).
                orElseThrow(() -> new ResourceNotFoundException("Stock amount not found with provided product id"));
        Boolean available = stockAmount.isAvailable();
        if (available == null) {
            throw new ResourceNotFoundException("Available of stock amount with provided product id equals null");
        }
        return available;
    }

    @GetMapping(path = "/avail/{available}")
    public Iterable<StockAmount> getAllByAvailable(@PathVariable Boolean available) {
        Iterable<StockAmount> stockAmounts = stockAmountRepository.findAllByAvailable(available);
        if (Iterables.size(stockAmounts) == 0) {
            throw new ResourceNotFoundException(
                    available ? "There are not any available stock amounts" : "There are not any unavailable stock amounts");
        }
        return stockAmounts;
    }

    @PostMapping(path = "/add")
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody
    String addStockAmount(@RequestBody StockAmount stockAmount) {
        if (stockAmount.getProductId()  == null || stockAmount.getMeasure()  == null || stockAmount.getAmount() == null) {
            throw new ResourceNotFoundException("Product id or measure or amount is/are not provided");
        }
        if (stockAmountRepository.existsStockAmountByProductId(stockAmount.getProductId())) {
            throw new ResourceNotFoundException("Stock amount for product with product id provided already exists");
        }
        if (stockAmount.getAmount() > 0.0) {
            stockAmount.setAvailable(true);
        } else {
            stockAmount.setAvailable(false);
        }
        stockAmountRepository.save(stockAmount);
        return "Saved";
    }

    // "metodę przyjmującą id produktu i na tej podstawie tworzył stan magazynowy"
    @PostMapping(path = "/add_empty")
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody
    String addEmptyStockAmount(@RequestBody StockAmount stockAmount) {
        if (stockAmount.getAmount() != null) {
            throw new ResourceNotFoundException("Amount is not null");
        }
        if (!stockAmountRepository.existsStockAmountByProductId(stockAmount.getProductId())) {
            stockAmount.setAvailable(false);
            stockAmountRepository.save(stockAmount);
        } else {
            throw new ResourceNotFoundException("Stock amount with provided product id exists");
        }
        return "Saved";
    }

    @PatchMapping("/increase")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    String increaseAmount(@RequestBody StockAmount newStockAmount) {
        if (newStockAmount.getProductId() == null || newStockAmount.getAmount() == null) {
            throw new ResourceNotFoundException("Product id or amount is/are not provided");
        }
        StockAmount stockAmount  = stockAmountRepository.findStockAmountByProductId(newStockAmount.getProductId()).
                orElseThrow(() -> new ResourceNotFoundException("StockAmount not found with product id"));

        Double newAmount = newStockAmount.getAmount();
        Double oldAmount = stockAmount.getAmount();
        if (oldAmount == null) {
            oldAmount = 0.0;
        }
        if (newAmount > 0.0) {
            stockAmount.setAmount(oldAmount + newAmount);
            if (!stockAmount.isAvailable()) {
                stockAmount.setAvailable(true);
            }
        } else {
            throw new UnprocessableEntityException("Amount is less than or equal 0");
        }
        stockAmountRepository.save(stockAmount);
        return "Increased";
    }

    @PatchMapping("/decrease")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    String decreaseAmount(@RequestBody StockAmount newStockAmount) {
        if (newStockAmount.getProductId() == null || newStockAmount.getAmount() == null) {
            throw new ResourceNotFoundException("Product id or amount is/are not provided");
        }
        StockAmount stockAmount  = stockAmountRepository.findStockAmountByProductId(newStockAmount.getProductId()).
                orElseThrow(() -> new ResourceNotFoundException("StockAmount not found with product id"));
        Double newAmount = newStockAmount.getAmount();
        Double oldAmount = stockAmount.getAmount();
        if (oldAmount == null) {
            throw new UnprocessableEntityException("old amount is null");
        }
        if (oldAmount - newAmount >= 0.0) {
            stockAmount.setAmount(oldAmount - newAmount);
            if (stockAmount.isAvailable() && stockAmount.getAmount() == 0.0) {
                stockAmount.setAvailable(false);
            }
        } else {
            throw new UnprocessableEntityException("New amount is greater than old amount");
        }
        stockAmountRepository.save(stockAmount);
        return "Decreased";
    }

    // ---

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path = "/measures")
    public Measure[] getAllMeasures() {
        Measure[] measures = Measure.class.getEnumConstants();
        return measures;
    }
}
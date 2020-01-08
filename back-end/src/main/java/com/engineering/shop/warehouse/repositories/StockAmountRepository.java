package com.engineering.shop.warehouse.repositories;

import org.springframework.data.repository.CrudRepository;

import com.engineering.shop.warehouse.models.StockAmount;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface StockAmountRepository extends CrudRepository<StockAmount, Integer> {
    Optional<StockAmount> findStockAmountByStockAmountId(Integer stockAmountId);
    Iterable<StockAmount> findAllByAvailable(Boolean available);
    Boolean existsStockAmountByProductId(Integer productId);
    Optional<StockAmount> findStockAmountByProductId(Integer productId);
    Iterable<StockAmount> findAllByProductId(Integer productId);
    Iterable<StockAmount> findAllByDateTimeBetween(LocalDateTime start, LocalDateTime end);
}
package com.engineering.shop.warehouse.repositories;

import org.springframework.data.repository.CrudRepository;

import com.engineering.shop.warehouse.models.StockAmount;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockAmountRepository extends CrudRepository<StockAmount, Integer> {

    Optional<StockAmount> findStockAmountByStockAmountId(Integer stockAmountId);
    Optional<StockAmount> findStockAmountByProductId(Integer productId);
    Iterable<StockAmount> findAllByAvailable(Boolean available);
    boolean existsStockAmountByProductId(Integer productId);
}
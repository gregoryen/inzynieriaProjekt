package com.warehouse.repositories;

import org.springframework.data.repository.CrudRepository;

import com.warehouse.models.StockAmount;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockAmountRepository extends CrudRepository<StockAmount, Integer> {

    Optional<StockAmount> findByStockAmountId(Integer stockAmountId);
    Optional<StockAmount> findByAvailable(Boolean available);
}
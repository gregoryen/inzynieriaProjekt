package com.engineering.shop.warehouse.repositories;

import com.engineering.shop.warehouse.models.StockAmountChange;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface StockAmountChangeRepository extends CrudRepository<StockAmountChange, Integer> {

    public Iterable<StockAmountChange> findAllByChangeDateTimeBetween(LocalDateTime start, LocalDateTime end);
}

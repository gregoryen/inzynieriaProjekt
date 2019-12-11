package com.warehouse.repositories;

import com.warehouse.models.StockAmount;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends CrudRepository<StockAmount, Integer> {

}

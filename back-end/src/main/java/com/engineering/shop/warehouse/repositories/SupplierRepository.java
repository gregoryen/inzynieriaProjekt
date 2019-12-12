package com.warehouse.repositories;

import com.warehouse.models.StockAmount;
import com.warehouse.models.Supplier;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplierRepository extends CrudRepository<Supplier, Integer> {

    Optional<Supplier> findBySupplierId(Integer SupplierId);
}

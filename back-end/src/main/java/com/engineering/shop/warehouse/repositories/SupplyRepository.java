package com.engineering.shop.warehouse.repositories;

import com.engineering.shop.warehouse.models.Supply;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplyRepository extends CrudRepository<Supply, Integer> {

    Optional<Supply> findBySupplyId(Integer supplyId);
}

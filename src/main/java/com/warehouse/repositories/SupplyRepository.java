package com.warehouse.repositories;

import com.warehouse.models.Supply;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplyRepository extends CrudRepository<Supply, Integer> {

    Optional<Supply> findBySupplyId(Integer supplyId);
}

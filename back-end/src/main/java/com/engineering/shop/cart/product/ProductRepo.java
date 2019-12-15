package com.engineering.shop.cart.product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// tesotowe repozytorium

@Repository
public interface ProductRepo extends CrudRepository<Product, Integer> {
}

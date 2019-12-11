package com.engineering.shop.products;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepo extends CrudRepository<Product, Integer> {

    Iterable<Product> findByName(String name);

    Iterable<Product> findByCategoryId(Integer categoryId);
}

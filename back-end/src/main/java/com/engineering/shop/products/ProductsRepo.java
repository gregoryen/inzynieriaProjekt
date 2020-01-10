package com.engineering.shop.products;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductsRepo extends JpaRepository<Product, Integer> {
    Iterable<Product> findByName(String name);

    Iterable<Product> findByNameContainingIgnoreCase(String name);

    Iterable<Product> findByMainCategoryId(Integer categoryId);

    Iterable<Product> findAllByNameContainingIgnoreCaseAndActiveIsTrue(String name);

    List<Product> findAllByActiveIsTrue();

    List<Product> findAllByIdIn(List<Integer> ids);

    Iterable<Product> findByMainCategoryIdAndActiveIsTrue(Integer categoryId);

    @Override
    @RestResource(exported = false)
    void deleteInBatch(Iterable<Product> iterable);

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();
}

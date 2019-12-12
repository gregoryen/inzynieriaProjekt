package com.engineering.shop.products;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RepositoryRestResource(collectionResourceRel = "products", path = "products")
public interface ProductsRepo extends CrudRepository<Product, Integer> {
    Optional<Product> getById(Integer id);

    Iterable<Product> findByName(String name);

    Iterable<Product> findByNameContainingIgnoreCase(String name);

    Iterable<Product> findByMainCategoryId(Integer categoryId);

    @Override
    @RestResource(exported = false)
    <S extends Product> S save(S s);

    @Override
    @RestResource(exported = false)
    <S extends Product> Iterable<S> saveAll(Iterable<S> iterable);
}

package com.engineering.shop.categories;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriesRepo extends CrudRepository<Category, Integer> {

    Optional<Category> findById(Integer id);

    Optional<Category> findByParentId(Integer parentId);

    Iterable<Category> findAllByParentId(Integer parentId);

    @Override
    <S extends Category> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    @CacheEvict("categoriesTree")
    <S extends Category> S save(S s);

    @Override
    @CacheEvict("categoriesTree")
    void deleteById(Integer integer);

    @Override
    @CacheEvict("categoriesTree")
    void delete(Category category);

    @Override
    @CacheEvict("categoriesTree")
    void deleteAll(Iterable<? extends Category> iterable);

    @Override
    @CacheEvict("categoriesTree")
    void deleteAll();
}

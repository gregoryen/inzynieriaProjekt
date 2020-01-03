package com.engineering.shop.categories;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriesRepo extends CrudRepository<Category, Integer> {

    Optional<Category> findById(Integer id);

    Optional<Category> findByParentIdAndPreviousCategoryId(Integer parentId,Integer previousCategoryId);

    Optional<Category> findByParentIdAndPreviousCategoryIdIsNull(Integer parentId);

    Iterable<Category> findAllByParentId(Integer parentId);

    Iterable<Category> findAllByParentIdIsNull();

    @Override
    <S extends Category> Iterable<S> saveAll(Iterable<S> iterable);

    @Override
    @CachePut("categoriesTree")
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

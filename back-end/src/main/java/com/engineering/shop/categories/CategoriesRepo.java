package com.engineering.shop.categories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriesRepo extends CrudRepository<Category, Integer> {

    Optional<Category> findById(Integer id);

    Optional<Category> findByParentId(Integer parentId);
}

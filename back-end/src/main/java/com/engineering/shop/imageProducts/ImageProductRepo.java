package com.engineering.shop.imageProducts;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageProductRepo extends CrudRepository<ImageProduct, Integer> {
}

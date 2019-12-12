package com.engineering.shop.imageProducts;

import com.engineering.shop.products.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageProductRepo extends CrudRepository<ImageProduct, Integer> {
}

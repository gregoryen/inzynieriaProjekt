package com.engineering.shop.imageAdv;

import com.engineering.shop.imageAdv.ImageAdv;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageAdvRepo extends CrudRepository<ImageAdv, Integer> {
    ImageAdv getById(Integer id);
}

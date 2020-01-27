package com.engineering.shop.opinions;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.engineering.shop.opinions.Opinion;
import java.util.Optional;

@Repository
public interface OpinionRepo extends CrudRepository<Opinion, Integer> {

        public Iterable<Opinion> findByClientEmail(String clientEmail);

        public Iterable<Opinion> findByProductId(Integer productId);
        }

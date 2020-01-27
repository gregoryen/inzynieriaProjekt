package com.engineering.shop.advertisement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertisementRepo extends JpaRepository<Advertisement, Integer> {






    @Override
    @RestResource(exported = false)
    void deleteInBatch(Iterable<Advertisement> iterable);

    @Override
    @RestResource(exported = false)
    void deleteAllInBatch();
}

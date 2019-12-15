package com.engineering.shop.cart.bucket;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BucketRepo extends CrudRepository<Bucket, Integer> {
    Optional<Bucket> findByBucketIndex (UUID id);
    //Iterable<Bucket> findBucketById (Integer id);
}

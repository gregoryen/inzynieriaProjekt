package com.engineering.shop.cart.bucket;

import com.engineering.shop.cart.bucketlist.BucketPosition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;


@Repository
public interface BucketRepo extends CrudRepository<Bucket, Integer> {
    Optional<Bucket> findByToken(String token);
    Boolean existsByToken(String token);
    void deleteByToken(String token);
}

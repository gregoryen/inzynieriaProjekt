package com.engineering.shop.cart.bucketlist;

import com.engineering.shop.cart.bucketlist.BucketPosition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BucketPositionRepo extends CrudRepository<BucketPosition, Integer> {

    Iterable<BucketPosition> findByBucketIndex(String id);

    void deleteByBucketIndexAndProductId(String bucketIndex, Integer productId);
    Optional<BucketPosition> findByBucketIndexAndProductId(String bucketIndex, Integer productId);

}

package com.engineering.shop.cart.bucketlist;

import com.engineering.shop.cart.bucketlist.BucketPosition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BucketPositionRepo extends CrudRepository<BucketPosition, Integer> {
    Iterable<BucketPosition> findByBucketIndex (String id);
   // BucketPosition findByBucketPositionId(Integer bucketPositionId);
    //Iterable<BucketPosition> findByBucketPositionId(Integer bucketPositionId);

}

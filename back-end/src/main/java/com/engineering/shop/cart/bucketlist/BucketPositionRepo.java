package com.engineering.shop.cart.bucketlist;

import com.engineering.shop.cart.bucketlist.BucketPosition;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BucketPositionRepo extends CrudRepository<BucketPosition, Integer> {

    BucketPosition findByBucketPositionId(Integer bucketPositionId);
    //Iterable<BucketPosition> findByBucketPositionId(Integer bucketPositionId);

}

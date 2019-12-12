package com.engineering.shop.bucketlist;

import com.engineering.shop.bucket.Bucket;
import org.springframework.data.repository.CrudRepository;

public interface BucketPositionRepo extends CrudRepository<BucketPosition, Integer> {

    Iterable<BucketPosition> findByBucketPositionId(Integer bucketPositionId);

}

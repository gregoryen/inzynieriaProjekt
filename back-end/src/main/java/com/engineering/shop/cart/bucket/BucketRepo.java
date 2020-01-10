package com.engineering.shop.cart.bucket;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BucketRepo extends CrudRepository<Bucket, Integer> {

    //Iterable<Bucket> findBucketById (Integer id);
}

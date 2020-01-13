package com.engineering.shop.cart.bucketlist;

import com.engineering.shop.cart.bucket.Bucket;
import com.engineering.shop.cart.bucketlist.BucketPosition;
import com.engineering.shop.products.Product;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BucketPositionRepo extends CrudRepository<BucketPosition, Integer> {

    Iterable<BucketPosition> findByBucket (Bucket bucket);
    Optional<BucketPosition> findBucketPositionByProductAndBucket (Product product, Bucket bucket);
    Boolean existsByProductIdAndBucket(Integer productId, Bucket bucket);
    Boolean existsByProductId (Integer productId);

}
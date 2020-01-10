package com.engineering.shop.cart.bucketlist;

import com.engineering.shop.cart.bucket.Bucket;
import com.engineering.shop.cart.bucketlist.BucketPosition;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BucketPositionRepo extends CrudRepository<BucketPosition, Integer> {

    // Metoda zwracajaca wszystkie produktu po id koszyka


}
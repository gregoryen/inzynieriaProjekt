package com.engineering.shop.cart.bucket;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BucketRepo extends CrudRepository<Bucket, Integer> {

    // Metoda szukajaca elementu w repo po id pozycji
    // Metoda szukajaca elementu w repo po id produktu ?

}

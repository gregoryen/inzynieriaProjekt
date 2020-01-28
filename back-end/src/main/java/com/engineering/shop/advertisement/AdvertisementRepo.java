package com.engineering.shop.advertisement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface AdvertisementRepo extends CrudRepository<Advertisement, Integer> {


@Transactional
@Query(value="select * from advertisement order by date desc",nativeQuery = true)
Collection<Advertisement> getAllSortedByDate();


}

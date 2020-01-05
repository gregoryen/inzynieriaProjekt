//package com.engineering.shop.warehouse.repositories;
//
//import com.engineering.shop.warehouse.models.StockAmount;
//import com.engineering.shop.warehouse.models.Supplier;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Optional;
//
//@Repository
//public interface SupplierRepository extends CrudRepository<Supplier, Integer> {
//    Optional<Supplier> findBySupplierId(Integer SupplierId);
//    Optional<Supplier> findSupplierByFirstnameAndLastnameAndPhoneNumberAndCompany(
//            String firstname, String lastname, String phoneNumber, String company
//    );
//    Boolean existsSupplierByFirstnameAndLastnameAndPhoneNumberAndCompany(
//            String firstname, String lastname, String phoneNumber, String company
//    );
//}

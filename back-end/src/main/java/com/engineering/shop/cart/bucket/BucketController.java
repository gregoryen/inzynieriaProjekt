package com.engineering.shop.cart.bucket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/bucket")
public class BucketController {

    private BucketRepo bucketRepo;

    // czy napewno autowired
    @Autowired
    public BucketController(BucketRepo bucketRepo) {
        this.bucketRepo = bucketRepo;
    }

    // Repo methods implementation
    @CrossOrigin
    @PostMapping("/add")
    public void createBucket() {

    }

    // dodanie do istniejacego koszyka
    @PostMapping("/add/{bucketId}/{bucketPositionId}")
    public void addToBucket(){

    }
    @GetMapping("/all")
    public Iterable<Bucket> getAll() {
        return bucketRepo.findAll();
    }

    @GetMapping("/getById/{id}")
    public Optional<Bucket> getById(@PathVariable("id") Integer id) {
        return bucketRepo.findById(id);
    }

    // usuniecie koszyka
    @DeleteMapping("delete/{id}")
    public void deleteById(@PathVariable("id") Integer id) {
        bucketRepo.deleteById(id);
    }

    // usuniecie elementu z koszyka po id
    @DeleteMapping("delete/product/{id}")
    public void deletePositionById(@PathVariable("id") Integer id) {

    }

    // utworzenie nowego koszyka
    // Pobranie ciala POJO, wywolanie transformera, dodanie produktu do listy i zapisanie go w bazie danych
    // aktualizacja wartosci koszyka na postawie danych z bucketposition

}

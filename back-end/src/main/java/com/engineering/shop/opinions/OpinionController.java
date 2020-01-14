package com.engineering.shop.opinions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import com.engineering.shop.opinions.Opinion;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/opinions")
public class OpinionController {

    private OpinionRepo opinionRepo;
    @Autowired
    public OpinionController(OpinionRepo opinionRepo) {

        this.opinionRepo = opinionRepo;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/all")
    public Iterable<Opinion> getAll() {

        return this.opinionRepo.findAll();
    }

    @GetMapping(path="{id}")
    public Optional<Opinion> getById(@PathVariable("id") Integer id) {
        return opinionRepo.findById(id);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/add")
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody String addOpinion(@RequestBody Opinion opinion) {

        opinionRepo.save(opinion);
        return "Saved";
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/update")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public @ResponseBody String updateOpinion(@RequestBody Opinion opinion) {

        opinionRepo.save(opinion);
        return "Updated";
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping(path = "{id}")
    public void deleteById(@PathVariable("id") Integer id){
        opinionRepo.deleteById(id);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/clientEmail/{email}")
    public Iterable<Opinion> getByClientEmail(@PathVariable("email") String email) {
        return opinionRepo.findByClientEmail(email);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/productId/{id}")
    public Iterable<Opinion> getByProductId(@PathVariable("id") Integer id) {
        return opinionRepo.findByProductId(id);
    }

}

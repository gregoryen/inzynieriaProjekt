package com.warehouse.controllers;

import com.warehouse.models.Supply;
import com.warehouse.repositories.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/supplies")
public class SupplyController {

    private SupplyRepository supplyRepository;

    @Autowired
    public SupplyController(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    @GetMapping(path = "/all")
    public Iterable<Supply> getAll() {
        return supplyRepository.findAll();
    }

    @GetMapping(path = "/{SupplyId}")
    public Supply getBySupplyId(@PathVariable Integer SupplyId) {
        Supply supply = supplyRepository.findBySupplyId(SupplyId).orElseThrow(() -> new ResourceNotFoundException("Supply not found with provided supply id"));
        return supply;
    }

    @PostMapping(path = "/add")
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody
    String addSupply(@RequestBody Supply Supply) {
        supplyRepository.save(Supply);
        return "Saved";
    }

    @PostMapping(path = "/update")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public @ResponseBody
    String updateSupply(@RequestBody Supply Supply) {
        supplyRepository.save(Supply);
        return "Updated";
    }
}
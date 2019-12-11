package com.warehouse.controllers;

import com.warehouse.models.Supplier;
import com.warehouse.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/suppliers")
public class SupplierController {

    private SupplierRepository supplierRepository;

    @Autowired
    public SupplierController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @GetMapping(path = "/all")
    public Iterable<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    @GetMapping(path = "/{supplierId}")
    public Optional<Supplier> getBySupplierId(@PathVariable Integer supplierId) {
        return supplierRepository.findBySupplierId(supplierId);
    }

//    @GetMapping(path = "/availability/{available}")
//    public Optional<Supplier> getByAvailability(@PathVariable Boolean available) {
//        return supplierRepository.findByAvailable(available);
//    }

    @PostMapping(path = "/add")
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody
    String addSupplier(@RequestBody Supplier supplier) {
        supplierRepository.save(supplier);
        return "Saved";
    }

    @PostMapping(path = "/update")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public @ResponseBody
    String updateSupplier(@RequestBody Supplier supplier) {
        supplierRepository.save(supplier);
        return "Updated";
    }
}

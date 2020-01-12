package com.engineering.shop.warehouse.controllers;


import com.engineering.shop.warehouse.models.Supplier;
import com.engineering.shop.warehouse.repositories.SupplierRepository;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(path = "/suppliers")
public class SupplierController {

    private SupplierRepository supplierRepository;

    @Autowired
    public SupplierController(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path = "/all")
    public Iterable<Supplier> getAll() {
        return supplierRepository.findAll();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path = "/{supplierId}")
    public Supplier getBySupplierId(@PathVariable Integer supplierId) {
        Supplier supplier = supplierRepository.findBySupplierId(supplierId).orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
        return supplier;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(path = "/add")
    @ResponseStatus(value = HttpStatus.CREATED)
    public @ResponseBody
    String addSupplier(@RequestBody Supplier supplier) {
        supplierRepository.save(supplier);
        return "Saved";
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping(path = "/update")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public @ResponseBody
    String updateSupplier(@RequestBody Supplier supplier) {
        supplierRepository.save(supplier);
        return "Updated";
    }

    // ---

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping(path = "/companies")
    public Set<String> getAllCompanies() {
        Iterable<Supplier> suppliers = supplierRepository.findAll();
        Set<String> companies = new HashSet<>();
        for (Supplier s : suppliers) {
            companies.add(s.getCompany());
        }
        return Sets.newHashSet(companies);
    }
}
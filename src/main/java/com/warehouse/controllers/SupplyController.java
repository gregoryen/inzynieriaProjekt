package com.warehouse.controllers;

import com.warehouse.repositories.SupplyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/supplies")
public class SupplyController {

    private SupplyRepository supplyRepository;

    @Autowired
    public SupplyController(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

}
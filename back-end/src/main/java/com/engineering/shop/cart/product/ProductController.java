package com.engineering.shop.cart.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    ProductRepo productRepo;

    @Autowired
    public ProductController(ProductRepo productRepo){
        this.productRepo = productRepo;
    }

    @GetMapping(path="/test")
    public @ResponseBody String test(){
        return "I can communicate with you ";
    }

    @GetMapping(path="/getAll")
    public Iterable<Product> getAllProducts(){
        return productRepo.findAll();
    }

    @GetMapping(path="/getById/{id}")
    public Optional<Product> getProductById(@PathVariable("id") Integer id){
        return productRepo.findById(id);
    }


    @PostMapping(path="/addProduct")
    public @ResponseBody String addProduct( @RequestBody Product product){
        productRepo.save(product);
        return "New Product Saved";
    }

    @DeleteMapping(path="/delete/{id}")
    public @ResponseBody String deleteProduct(@PathVariable("id") Integer id){
        productRepo.deleteById(id);
        return "Product deleted";
    }

}




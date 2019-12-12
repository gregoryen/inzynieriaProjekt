package com.engineering.shop.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
    private CategoriesRepo categoriesRepo;

    @Autowired
    public CategoriesController(CategoriesRepo categoriesRepo) {
        this.categoriesRepo = categoriesRepo;
    }

    @GetMapping("/children")
    public Optional<Category> getChildren(@RequestParam Integer parentId) {
        return categoriesRepo.findByParentId(parentId);
    }
}

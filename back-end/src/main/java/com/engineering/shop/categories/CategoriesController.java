package com.engineering.shop.categories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
    private CategoriesRepo categoriesRepo;

    @Autowired
    public CategoriesController(CategoriesRepo categoriesRepo) {
        this.categoriesRepo = categoriesRepo;
    }

    @GetMapping("/all")
    public Iterable<Category> getAll() {
        return categoriesRepo.findAll();
    }

    @GetMapping("/children")
    public Optional<Category> getChildren(@RequestParam Integer parentId) {
        return categoriesRepo.findByParentId(parentId);
    }

    @GetMapping
    public Optional<Category> getById(@RequestParam Integer id) {
        return categoriesRepo.findById(id);
    }

    @PostMapping
    public Category addCategory(@RequestBody Category category) {
        return categoriesRepo.save(category);
    }

    @PutMapping
    public Category updateCategory(@RequestBody Category category) {
        return categoriesRepo.save(category);
    }

    @DeleteMapping
    public void deleteCategory(@RequestParam Integer id) {
        categoriesRepo.deleteById(id);
    }
}

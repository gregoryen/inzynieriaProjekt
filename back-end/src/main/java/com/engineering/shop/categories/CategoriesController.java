package com.engineering.shop.categories;

import com.engineering.shop.products.Product;
import com.engineering.shop.products.ProductsRepo;
import com.google.common.collect.Iterables;
import lombok.Data;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
    private CategoriesRepo categoriesRepo;
    private CategoryValidator categoryValidator;
    private ProductsRepo productsRepo;

    @Autowired
    public CategoriesController(CategoriesRepo categoriesRepo, CategoryValidator categoryValidator, ProductsRepo productsRepo) {
        this.categoriesRepo = categoriesRepo;
        this.categoryValidator = categoryValidator;
        this.productsRepo = productsRepo;
    }

    @GetMapping("/children")
    public Iterable<Category> getChildren(@RequestParam Integer parentId) {
        ArrayList<Category> children = new ArrayList<>();
        Optional<Category> next = categoriesRepo.findByParentIdAndPreviousCategoryIdIsNull(parentId);
        while (next.isPresent()) {
            Category category = next.get();
            children.add(category);
            next = categoriesRepo.findByParentIdAndPreviousCategoryId(parentId, category.getId());
        }
        return children;
    }

    @GetMapping("/mainCategories")
    public Iterable<Category> getMainCategories() {
        ArrayList<Category> mainCategories = new ArrayList<>();
        Optional<Category> next = categoriesRepo.findByParentIdIsNullAndPreviousCategoryIdIsNull();
        while (next.isPresent()) {
            Category category = next.get();
            mainCategories.add(category);
            next = categoriesRepo.findByParentIdIsNullAndPreviousCategoryId(category.getId());
        }
        return mainCategories;
    }

    @GetMapping("/withoutProducts")
    public Iterable<Category> getCategoriesWithoutProducts() {
        Iterable<Category> allCategories = categoriesRepo.findAll();
        List<Category> categories = IteratorUtils.toList(allCategories.iterator());

        Iterable<Product> allProducts = productsRepo.findAll();
        List<Product> products = IteratorUtils.toList(allProducts.iterator());

        List<Integer> idUsedCategories = products.stream().map(Product::getMainCategoryId).distinct().collect(Collectors.toList());

        idUsedCategories.forEach( e -> {
            categories.removeIf((element) -> element.getId().equals(e));
        });

        return categories;
    }

    @GetMapping("/tree")
    public Iterable<TreeNode> getCategoriesTree() {
        Iterable<Category> allCategories = categoriesRepo.findAll();
        List<Category> categories = IteratorUtils.toList(allCategories.iterator());
        categories.forEach(c -> c.add(ControllerLinkBuilder.linkTo(CategoriesController.class).slash(c.getId()).withSelfRel()));
        Collection<Category> rootCategories = categories.stream().filter(category -> category.getParentId() == null).collect(Collectors.toSet());
        categories.removeAll(rootCategories);
        List<TreeNode> trees = rootCategories.stream().map(TreeNode::new).collect(Collectors.toList());
        trees.forEach(treeNode -> buildTree(treeNode, categories));
        return trees;
    }

    private void buildTree(TreeNode root, List<Category> categories) {
        List<Category> children = categories.stream().filter(category -> category.getParentId().equals(root.getCategory().getId())).collect(Collectors.toList());
        categories.removeAll(children);

        Category topChild = new Category();
        List<Category> sortedChildren = new ArrayList<>();
        while (children.size() > 0) {
            Integer topChildId = topChild.getId();
            topChild = children.stream().filter(child -> Objects.equals(topChildId, child.getPreviousCategoryId())).findFirst().orElseThrow(IllegalArgumentException::new);
            sortedChildren.add(topChild);
            children.remove(topChild);
        }

        List<TreeNode> childNodes = sortedChildren.stream().map(TreeNode::new).collect(Collectors.toList());
        root.setChildren(childNodes);
        childNodes.forEach(node -> this.buildTree(node, categories));
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteCategory(@RequestParam Integer id) {
        Iterable<Product> products = productsRepo.findByMainCategoryId(id);
        if (Iterables.size(products) > 0) {
            return new ResponseEntity<>(
                    new DeleteCategoryMessage("Do tej kategorii sa przypisane produkty"),
                    HttpStatus.FORBIDDEN);
        }
        Optional<Category> optionalDeletCategory = categoriesRepo.findById(id);
        if (optionalDeletCategory.isPresent()) {
            Optional<Category> optionalCategory = categoriesRepo.findByPreviousCategoryId(id);
            if (optionalCategory.isPresent()) {
                Category category = optionalCategory.get();
                category.setPreviousCategoryId(optionalDeletCategory.get().getPreviousCategoryId());
                categoriesRepo.save(category);
            }
            categoriesRepo.deleteById(id);
        }
         return new ResponseEntity<>(
                 new DeleteCategoryMessage("Kategoria została usunieta"),
                HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping
    public Category addCategory(@RequestBody @Validated Category category) {
        Optional<Category> optionalCategoryToModify = categoriesRepo.findByParentIdAndPreviousCategoryId(category.getParentId(), category.getPreviousCategoryId());
        Category toReturn = categoriesRepo.save(category);
        if (optionalCategoryToModify.isPresent()) {
            Category categoryToModify = optionalCategoryToModify.get();
            categoryToModify.setPreviousCategoryId(toReturn.getId());
            categoriesRepo.save(categoryToModify);
        }
        return toReturn;
    }

    @Data
    private class TreeNode {
        Category category;
        Iterable<TreeNode> children;

        TreeNode(Category category) {
            this.category = category;
            this.children = new ArrayList<>();
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @InitBinder("category")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(categoryValidator);
    }
}

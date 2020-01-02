package com.engineering.shop.categories;

import lombok.Data;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.hateoas.server.mvc.ControllerLinkBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
    private CategoriesRepo categoriesRepo;

    @Autowired
    public CategoriesController(CategoriesRepo categoriesRepo) {
        this.categoriesRepo = categoriesRepo;
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

    @Cacheable("categoriesTree")
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

    @Data
    private class TreeNode {
        Category category;
        Iterable<TreeNode> children;

        TreeNode(Category category) {
            this.category = category;
            this.children = new ArrayList<>();
        }
    }
}

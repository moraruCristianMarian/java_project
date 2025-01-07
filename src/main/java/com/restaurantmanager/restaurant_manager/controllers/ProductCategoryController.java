package com.restaurantmanager.restaurant_manager.controllers;

import com.restaurantmanager.restaurant_manager.entities.ProductCategory;
import com.restaurantmanager.restaurant_manager.services.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/productCategorys")
public class ProductCategoryController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductCategory> getProductCategoryById(@PathVariable Integer id) {
        ProductCategory foundProductCategory = productCategoryService.getById(id)
                                                                     .stream()
                                                                     .findFirst()
                                                                     .orElse(null);
        if (foundProductCategory != null)
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(foundProductCategory);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(null);
    }

    @GetMapping
    public ResponseEntity<List<ProductCategory>> getAllProductCategorys() {
        List<ProductCategory> productCategorys = productCategoryService.getAllProductCategorysUsingJpa();
        return ResponseEntity.status(HttpStatus.OK)
                             .body(productCategorys);
    }

    @PostMapping
    public ResponseEntity<ProductCategory> createProductCategory(@RequestBody @Valid ProductCategory productCategory) {
        ProductCategory createdProductCategory = productCategoryService.createProductCategory(productCategory);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(createdProductCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategory> updateProductCategory(@PathVariable Integer id, @RequestBody @Valid ProductCategory updatedProductCategory) {
        try {
            ProductCategory productCategory = productCategoryService.updateProductCategory(id, updatedProductCategory);
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(productCategory);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductCategory(@PathVariable Integer id) {
        try {
            productCategoryService.deleteProductCategory(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                                 .body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }
    }
}
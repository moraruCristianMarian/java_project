package com.restaurantmanager.restaurant_manager.controllers;

import com.restaurantmanager.restaurant_manager.entities.Product;
import com.restaurantmanager.restaurant_manager.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        Product foundProduct = productService.getById(id)
                                             .stream()
                                             .findFirst()
                                             .orElse(null);
        if (foundProduct != null)
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(foundProduct);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(null);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(value = "sorted", required = false, defaultValue = "false") boolean sorted,
            @RequestParam(value = "excludedIngredient", required = false) Integer excludedIngredient
            )
    {
        List<Product> products;

        if (sorted)
            products = productService.getAllProductsUsingJpaByCost();
        else
        {
            if (excludedIngredient != null)
                products = productService.getProductsFilterIngredient(excludedIngredient);
            else
                products = productService.getAllProductsUsingJpa();
        }

        return ResponseEntity.status(HttpStatus.OK)
                             .body(products);
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid Product product) {
        Product createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody @Valid Product updatedProduct) {
        try {
            Product product = productService.updateProduct(id, updatedProduct);
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(product);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                                 .body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }
    }
}
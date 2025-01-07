package com.restaurantmanager.restaurant_manager.controllers;

import com.restaurantmanager.restaurant_manager.entities.IngredientInProduct;
import com.restaurantmanager.restaurant_manager.services.IngredientInProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/ingredientInProducts")
public class IngredientInProductController {
    @Autowired
    private IngredientInProductService ingredientInProductService;

    @GetMapping("/{ingredientId}/{productId}")
    public ResponseEntity<IngredientInProduct> getIngredientInProductById(@PathVariable Integer ingredientId,
                                                                          @PathVariable Integer productId) {
        IngredientInProduct foundIngredientInProduct = ingredientInProductService.getById(ingredientId, productId)
                                                                                 .stream()
                                                                                 .findFirst()
                                                                                 .orElse(null);
        if (foundIngredientInProduct != null)
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(foundIngredientInProduct);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(null);
    }

    @GetMapping
    public ResponseEntity<List<IngredientInProduct>> getAllIngredientInProducts() {
        List<IngredientInProduct> ingredientInProducts = ingredientInProductService.getAllIngredientInProductsUsingJpa();
        return ResponseEntity.status(HttpStatus.OK)
                             .body(ingredientInProducts);
    }

    @PostMapping
    public ResponseEntity<IngredientInProduct> createIngredientInProduct(@RequestBody @Valid IngredientInProduct ingredientInProduct) {
        IngredientInProduct createdIngredientInProduct = ingredientInProductService.createIngredientInProduct(ingredientInProduct);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(createdIngredientInProduct);
    }

    @PutMapping("/{ingredientId}/{productId}")
    public ResponseEntity<IngredientInProduct> updateIngredientInProduct(@PathVariable Integer ingredientId,
                                                                         @PathVariable Integer productId,
                                                                         @RequestBody @Valid IngredientInProduct updatedIngredientInProduct) {
        try {
            IngredientInProduct ingredientInProduct = ingredientInProductService.updateIngredientInProduct(ingredientId, productId, updatedIngredientInProduct);
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(ingredientInProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }
    }

    @DeleteMapping("/{ingredientId}/{productId}")
    public ResponseEntity<Void> deleteIngredientInProduct(@PathVariable Integer ingredientId, @PathVariable Integer productId) {
        try {
            ingredientInProductService.deleteIngredientInProduct(ingredientId, productId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                                 .body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }
    }
}
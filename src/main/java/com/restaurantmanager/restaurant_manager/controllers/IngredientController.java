package com.restaurantmanager.restaurant_manager.controllers;

import com.restaurantmanager.restaurant_manager.entities.Ingredient;
import com.restaurantmanager.restaurant_manager.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable Integer id) {
        Ingredient foundIngredient = ingredientService.getById(id)
                                                      .stream()
                                                      .findFirst()
                                                      .orElse(null);
        if (foundIngredient != null)
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(foundIngredient);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(null);
    }

    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientService.getAllIngredientsUsingJpa();
        return ResponseEntity.status(HttpStatus.OK)
                             .body(ingredients);
    }

    @PostMapping
    public ResponseEntity<Ingredient> createIngredient(@RequestBody @Valid Ingredient ingredient) {
        Ingredient createdIngredient = ingredientService.createIngredient(ingredient);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(createdIngredient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable Integer id, @RequestBody @Valid Ingredient updatedIngredient) {
        try {
            Ingredient ingredient = ingredientService.updateIngredient(id, updatedIngredient);
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(ingredient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable Integer id) {
        try {
            ingredientService.deleteIngredient(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                                 .body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }
    }
}
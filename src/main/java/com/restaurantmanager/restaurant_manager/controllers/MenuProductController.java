package com.restaurantmanager.restaurant_manager.controllers;

import com.restaurantmanager.restaurant_manager.entities.MenuProduct;
import com.restaurantmanager.restaurant_manager.services.MenuProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/menuProducts")
public class MenuProductController {
    @Autowired
    private MenuProductService menuProductService;

    @GetMapping("/{productId}/{restaurantId}")
    public ResponseEntity<MenuProduct> getMenuProductById(@PathVariable Integer productId,
                                                          @PathVariable Integer restaurantId) {
        MenuProduct foundMenuProduct = menuProductService.getById(productId, restaurantId)
                                                         .stream()
                                                         .findFirst()
                                                         .orElse(null);
        if (foundMenuProduct != null)
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(foundMenuProduct);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(null);
    }

    @GetMapping
    public ResponseEntity<List<MenuProduct>> getAllMenuProducts(
            @RequestParam(value = "minCost", required = false) Double minCost,
            @RequestParam(value = "maxCost", required = false) Double maxCost)
    {
        List<MenuProduct> menuProducts;

        if (minCost != null || maxCost != null) {
            menuProducts = menuProductService.getAllMenuProductsUsingJpaFilterCost(minCost, maxCost);
        } else {
            menuProducts = menuProductService.getAllMenuProductsUsingJpa();
        }

        return ResponseEntity.status(HttpStatus.OK)
                             .body(menuProducts);
    }

    @PostMapping
    public ResponseEntity<MenuProduct> createMenuProduct(@RequestBody @Valid MenuProduct menuProduct) {
        MenuProduct createdMenuProduct = menuProductService.createMenuProduct(menuProduct);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(createdMenuProduct);
    }

    @PutMapping("/{productId}/{restaurantId}")
    public ResponseEntity<MenuProduct> updateMenuProduct(@PathVariable Integer productId,
                                                         @PathVariable Integer restaurantId,
                                                         @RequestBody @Valid MenuProduct updatedMenuProduct) {
        try {
            MenuProduct menuProduct = menuProductService.updateMenuProduct(productId, restaurantId, updatedMenuProduct);
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(menuProduct);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }
    }

    @DeleteMapping("/{productId}/{restaurantId}")
    public ResponseEntity<Void> deleteMenuProduct(@PathVariable Integer productId, @PathVariable Integer restaurantId) {
        try {
            menuProductService.deleteMenuProduct(productId, restaurantId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }
}
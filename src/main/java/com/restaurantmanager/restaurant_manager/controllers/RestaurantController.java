package com.restaurantmanager.restaurant_manager.controllers;

import com.restaurantmanager.restaurant_manager.entities.Restaurant;
import com.restaurantmanager.restaurant_manager.services.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/restaurants")
public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;

    @GetMapping("/{id}")
    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Integer id) {
        Restaurant foundRestaurant = restaurantService.getById(id)
                                                      .stream()
                                                      .findFirst()
                                                      .orElse(null);
        if (foundRestaurant != null)
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(foundRestaurant);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                             .body(null);
    }

    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurantsUsingJpa();
        return ResponseEntity.status(HttpStatus.OK)
                             .body(restaurants);
    }

    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@RequestBody @Valid Restaurant restaurant) {
        Restaurant createdRestaurant = restaurantService.createRestaurant(restaurant);
        return ResponseEntity.status(HttpStatus.CREATED)
                             .body(createdRestaurant);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Integer id, @RequestBody @Valid Restaurant updatedRestaurant) {
        try {
            Restaurant restaurant = restaurantService.updateRestaurant(id, updatedRestaurant);
            return ResponseEntity.status(HttpStatus.OK)
                                 .body(restaurant);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable Integer id) {
        try {
            restaurantService.deleteRestaurant(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                                 .body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                 .body(null);
        }
    }
}
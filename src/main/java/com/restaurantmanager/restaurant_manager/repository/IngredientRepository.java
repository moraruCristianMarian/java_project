package com.restaurantmanager.restaurant_manager.repository;

import com.restaurantmanager.restaurant_manager.entities.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
//    List<Ingredient> findById(String id);
}
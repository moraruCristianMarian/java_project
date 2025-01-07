package com.restaurantmanager.restaurant_manager.repository;

import com.restaurantmanager.restaurant_manager.entities.IngredientInProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IngredientInProductRepository extends JpaRepository<IngredientInProduct, Integer> {
    Optional<IngredientInProduct> findByIngredientIdAndProductId(Integer ingredientId, Integer productId);

    void deleteByIngredientIdAndProductId(Integer ingredientId, Integer productId);

    boolean existsByIngredientIdAndProductId(Integer ingredientId, Integer productId);
}
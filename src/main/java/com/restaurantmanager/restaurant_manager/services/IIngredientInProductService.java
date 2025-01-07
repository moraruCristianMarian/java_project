package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.IngredientInProduct;

import java.util.List;

public interface IIngredientInProductService {
    List<IngredientInProduct> getById(Integer ingredientId, Integer productId);

    List<IngredientInProduct> getAllIngredientInProductsUsingJpa();

    IngredientInProduct createIngredientInProduct(IngredientInProduct ingredientInProduct);

    IngredientInProduct updateIngredientInProduct(Integer ingredientId, Integer productId, IngredientInProduct updatedIngredientInProduct);

    void deleteIngredientInProduct(Integer ingredientId, Integer productId);
}

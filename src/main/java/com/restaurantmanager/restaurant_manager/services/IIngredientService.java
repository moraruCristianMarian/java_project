package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.Ingredient;

import java.util.List;

public interface IIngredientService {
    List<Ingredient> getById(Integer id);

    List<Ingredient> getAllIngredientsUsingJpa();

    Ingredient createIngredient(Ingredient ingredient);

    Ingredient updateIngredient(Integer id, Ingredient updatedIngredient);

    void deleteIngredient(Integer id);
}

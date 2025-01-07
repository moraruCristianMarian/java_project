package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.Ingredient;

import com.restaurantmanager.restaurant_manager.repository.IngredientRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class IngredientService implements IIngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    @Override
    public List<Ingredient> getById(Integer id) {
        return this.ingredientRepository.findById(id)
                                        .stream()
                                        .collect(Collectors.toList());
    }

    @Override
    public List<Ingredient> getAllIngredientsUsingJpa() {
        return this.ingredientRepository.findAll();
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        if (ingredient.getId() != null && ingredientRepository.existsById(ingredient.getId())) {
            throw new IllegalArgumentException("Ingredient already exists.");
        }
        return ingredientRepository.save(ingredient);
    }

    public Ingredient updateIngredient(Integer id, Ingredient updatedIngredient) {
        return ingredientRepository.findById(id).map(
                existingIngredient -> {
                    existingIngredient.setName(updatedIngredient.getName());
                    existingIngredient.setIsMeat(updatedIngredient.getIsMeat());
                    existingIngredient.setIsDairy(updatedIngredient.getIsDairy());
                    existingIngredient.setIsGluten(updatedIngredient.getIsGluten());

                    return ingredientRepository.save(existingIngredient);
                }
        ).orElseThrow(() -> new IllegalArgumentException("Ingredient #" + id + " not found."));
    }

    public void deleteIngredient(Integer id) {
        if (!ingredientRepository.existsById(id)) {
            throw new IllegalArgumentException("Ingredient #" + id + " not found.");
        }
        ingredientRepository.deleteById(id);
    }
}

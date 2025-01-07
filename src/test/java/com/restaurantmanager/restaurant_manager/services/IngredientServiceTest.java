package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.Ingredient;
import com.restaurantmanager.restaurant_manager.repository.IngredientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceTest {
    @InjectMocks
    private IngredientService ingredientService;

    @Mock
    private IngredientRepository ingredientRepository;

    @Test
    void testCreateIngredient() {
        Ingredient ingredient = new Ingredient(1, "Carne de pui", true, false, false);

        Ingredient savedIngredient = new Ingredient(1, "Carne de pui", true, false, false);
        when(ingredientRepository.save(ingredient)).thenReturn(savedIngredient);

        Ingredient resultIngredient = ingredientService.createIngredient(ingredient);

        assertNotNull(resultIngredient);

        assertEquals(ingredient.getName(), resultIngredient.getName());
        assertEquals(savedIngredient.getName(), resultIngredient.getName());
        assertEquals(savedIngredient.getIsMeat(), resultIngredient.getIsMeat());
        assertEquals(savedIngredient.getIsDairy(), resultIngredient.getIsDairy());
        assertEquals(savedIngredient.getIsGluten(), resultIngredient.getIsGluten());

        verify(ingredientRepository).save(ingredient);
    }

    @Test
    void testGetById() {
        Ingredient ingredient = new Ingredient(1, "Carne de pui", true, false, false);

        when(ingredientRepository.findById(1)).thenReturn(Optional.of(ingredient));

        List<Ingredient> result = ingredientService.getById(1);

        assertNotNull(result);
        assertEquals(ingredient, result.get(0));

        verify(ingredientRepository).findById(1);
    }

    @Test
    void testGetAllIngredientsUsingJpa() {
        Ingredient ingredient1 = new Ingredient(1, "Cascaval", false, true, false);
        Ingredient ingredient2 = new Ingredient(1, "Faina", false, false, true);

        when(ingredientRepository.findAll()).thenReturn(Arrays.asList(ingredient1, ingredient2));

        List<Ingredient> result = ingredientService.getAllIngredientsUsingJpa();

        assertNotNull(result);
        assertEquals(ingredient1, result.get(0));
        assertEquals(ingredient2, result.get(1));

        verify(ingredientRepository).findAll();
    }

    @Test
    void testUpdateIngredient() {
        Ingredient ingredient = new Ingredient(1, "cascaval", true, false, true);
        Ingredient updatedIngredient = new Ingredient(1, "Cascaval", false, true, false);

        when(ingredientRepository.findById(1)).thenReturn(Optional.of(ingredient));
        when(ingredientRepository.save(ingredient)).thenReturn(updatedIngredient);

        Ingredient result = ingredientService.updateIngredient(1, updatedIngredient);

        assertNotNull(result);
        assertEquals(updatedIngredient.getName(), result.getName());
        assertEquals(updatedIngredient.getIsMeat(), result.getIsMeat());
        assertEquals(updatedIngredient.getIsDairy(), result.getIsDairy());
        assertEquals(updatedIngredient.getIsGluten(), result.getIsGluten());

        verify(ingredientRepository).save(ingredient);
    }

    @Test
    void testDeleteIngredient() {
        when(ingredientRepository.existsById(1)).thenReturn(true);

        ingredientService.deleteIngredient(1);

        verify(ingredientRepository).deleteById(1);
    }
}

package com.restaurantmanager.restaurant_manager.controllers;

import com.restaurantmanager.restaurant_manager.entities.Ingredient;
import com.restaurantmanager.restaurant_manager.services.IngredientService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {
    @InjectMocks
    private IngredientController ingredientController;

    @Mock
    private IngredientService ingredientService;

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void initMvcSiMapper() {
        mvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetIngredientById() throws Exception {
        Ingredient ingredient = new Ingredient(1, "Lapte", false, true, false);

        when(ingredientService.getById(1)).thenReturn(Stream.of(ingredient).toList());

        mvc.perform(get("/ingredients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Lapte"))
                .andExpect(jsonPath("$.isMeat").value(false))
                .andExpect(jsonPath("$.isDairy").value(true))
                .andExpect(jsonPath("$.isGluten").value(false));
    }

    @Test
    void testGetAllIngredients() throws Exception {
        Ingredient ingredient1 = new Ingredient(1, "Lapte", false, true, false);
        Ingredient ingredient2 = new Ingredient(2, "Carne de pui", true, false, false);

        when(ingredientService.getAllIngredientsUsingJpa()).thenReturn(Arrays.asList(ingredient1, ingredient2));

        mvc.perform(get("/ingredients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void testCreateIngredient() throws Exception {
        Ingredient ingredient = new Ingredient(null, "Lapte", false, true, false);
        Ingredient createdIngredient = new Ingredient(1, "Faina", false, false, true);

        when(ingredientService.createIngredient(any(Ingredient.class))).thenReturn(createdIngredient);

        mvc.perform(post("/ingredients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredient)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Faina"));
    }

    @Test
    void testUpdateIngredient() throws Exception {
        Ingredient updatedIngredient = new Ingredient(1, "Lapte", false, true, false);

        when(ingredientService.updateIngredient(eq(1), any(Ingredient.class))).thenReturn(updatedIngredient);

        mvc.perform(put("/ingredients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedIngredient)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Lapte"));
    }

    @Test
    void testDeleteIngredient() throws Exception {
        doNothing().when(ingredientService).deleteIngredient(1);

        mvc.perform(delete("/ingredients/1"))
                .andExpect(status().isNoContent());
    }
}

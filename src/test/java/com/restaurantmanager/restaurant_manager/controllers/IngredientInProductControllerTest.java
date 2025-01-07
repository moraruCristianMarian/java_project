package com.restaurantmanager.restaurant_manager.controllers;

import com.restaurantmanager.restaurant_manager.entities.IngredientInProduct;
import com.restaurantmanager.restaurant_manager.entities.IngredientInProductId;
import com.restaurantmanager.restaurant_manager.services.IngredientInProductService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
class IngredientInProductControllerTest {
    @InjectMocks
    private IngredientInProductController ingredientInProductController;

    @Mock
    private IngredientInProductService ingredientInProductService;

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void initMvcSiMapper() {
        mvc = MockMvcBuilders.standaloneSetup(ingredientInProductController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetIngredientInProductById() throws Exception {
        IngredientInProduct ingredientInProduct = new IngredientInProduct(new IngredientInProductId(1, 1));

        when(ingredientInProductService.getById(1, 1)).thenReturn(Stream.of(ingredientInProduct).toList());

        mvc.perform(get("/ingredientInProducts/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(new IngredientInProductId(1, 1)));
    }

    @Test
    void testGetAllIngredientInProducts() throws Exception {
        IngredientInProduct ingredientInProduct1 = new IngredientInProduct(new IngredientInProductId(1, 1));
        IngredientInProduct ingredientInProduct2 = new IngredientInProduct(new IngredientInProductId(2, 1));

        when(ingredientInProductService.getAllIngredientInProductsUsingJpa()).thenReturn(Arrays.asList(ingredientInProduct1, ingredientInProduct2));

        mvc.perform(get("/ingredientInProducts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(new IngredientInProductId(1, 1)))
                .andExpect(jsonPath("$[1].id").value(new IngredientInProductId(2, 1)));
    }

    @Test
    void testCreateIngredientInProduct() throws Exception {
        IngredientInProduct ingredientInProduct = new IngredientInProduct(new IngredientInProductId(null, 1));
        IngredientInProduct createdIngredientInProduct = new IngredientInProduct(new IngredientInProductId(1, 2));

        when(ingredientInProductService.createIngredientInProduct(any(IngredientInProduct.class))).thenReturn(createdIngredientInProduct);

        mvc.perform(post("/ingredientInProducts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredientInProduct)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(new IngredientInProductId(1, 2)));
    }

    @Test
    void testUpdateIngredientInProduct() throws Exception {
        IngredientInProduct updatedIngredientInProduct = new IngredientInProduct(new IngredientInProductId(1, 1));

        when(ingredientInProductService.updateIngredientInProduct(eq(1), eq(1), any(IngredientInProduct.class))).thenReturn(updatedIngredientInProduct);

        mvc.perform(put("/ingredientInProducts/1/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedIngredientInProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(new IngredientInProductId(1, 1)));
    }

    @Test
    void testDeleteIngredientInProduct() throws Exception {
        doNothing().when(ingredientInProductService).deleteIngredientInProduct(1, 1);

        mvc.perform(delete("/ingredientInProducts/1/1"))
                .andExpect(status().isNoContent());
    }
}

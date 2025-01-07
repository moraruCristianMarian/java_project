package com.restaurantmanager.restaurant_manager.controllers;

import com.restaurantmanager.restaurant_manager.entities.ProductCategory;
import com.restaurantmanager.restaurant_manager.services.ProductCategoryService;
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
class ProductCategoryControllerTest {
    @InjectMocks
    private ProductCategoryController productCategoryController;

    @Mock
    private ProductCategoryService productCategoryService;

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void initMvcSiMapper() {
        mvc = MockMvcBuilders.standaloneSetup(productCategoryController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetProductCategoryById() throws Exception {
        ProductCategory productCategory = new ProductCategory(1, "Mic dejun");

        when(productCategoryService.getById(1)).thenReturn(Stream.of(productCategory).toList());

        mvc.perform(get("/productCategories/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Mic dejun"));
    }

    @Test
    void testGetAllProductCategories() throws Exception {
        ProductCategory productCategory1 = new ProductCategory(1, "Garnituri");
        ProductCategory productCategory2 = new ProductCategory(2, "Burgeri");

        when(productCategoryService.getAllProductCategoriesUsingJpa()).thenReturn(Arrays.asList(productCategory1, productCategory2));

        mvc.perform(get("/productCategories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void testCreateProductCategory() throws Exception {
        ProductCategory productCategory = new ProductCategory(null, "Desert");
        ProductCategory createdProductCategory = new ProductCategory(1, "Mic dejun");

        when(productCategoryService.createProductCategory(any(ProductCategory.class))).thenReturn(createdProductCategory);

        mvc.perform(post("/productCategories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productCategory)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Mic dejun"));
    }

    @Test
    void testUpdateProductCategory() throws Exception {
        ProductCategory updatedProductCategory = new ProductCategory(1, "Desert");

        when(productCategoryService.updateProductCategory(eq(1), any(ProductCategory.class))).thenReturn(updatedProductCategory);

        mvc.perform(put("/productCategories/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProductCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Desert"));
    }

    @Test
    void testDeleteProductCategory() throws Exception {
        doNothing().when(productCategoryService).deleteProductCategory(1);

        mvc.perform(delete("/productCategories/1"))
                .andExpect(status().isNoContent());
    }
}

package com.restaurantmanager.restaurant_manager.controllers;

import com.restaurantmanager.restaurant_manager.entities.Product;
import com.restaurantmanager.restaurant_manager.entities.ProductCategory;
import com.restaurantmanager.restaurant_manager.services.ProductService;
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
class ProductControllerTest {
    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductService productService;

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void initMvcSiMapper() {
        mvc = MockMvcBuilders.standaloneSetup(productController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetProductById() throws Exception {
        Product product = new Product(1, new ProductCategory(), "Cheeseburger", 26.99);

        when(productService.getById(1)).thenReturn(Stream.of(product).toList());

        mvc.perform(get("/products/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Cheeseburger"));
    }

    @Test
    void testGetAllProducts() throws Exception {
        Product product1 = new Product(1, new ProductCategory(), "Paste Carbonara", 31.99);
        Product product2 = new Product(2, new ProductCategory(), "Omleta", 20.99);

        when(productService.getAllProductsUsingJpa()).thenReturn(Arrays.asList(product1, product2));

        mvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    @Test
    void testCreateProduct() throws Exception {
        Product product = new Product(null, new ProductCategory(),"Somon afumat", 36.99);
        Product createdProduct = new Product(1, new ProductCategory(), "Lava cake", 24.99);

        when(productService.createProduct(any(Product.class))).thenReturn(createdProduct);

        mvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Lava cake"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        Product updatedProduct = new Product(1, new ProductCategory(), "Omleta", 21.99);

        when(productService.updateProduct(eq(1), any(Product.class))).thenReturn(updatedProduct);

        mvc.perform(put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Omleta"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).deleteProduct(1);

        mvc.perform(delete("/products/1"))
                .andExpect(status().isNoContent());
    }
}

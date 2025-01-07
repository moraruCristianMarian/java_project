package com.restaurantmanager.restaurant_manager.controllers;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.restaurantmanager.restaurant_manager.entities.*;
import com.restaurantmanager.restaurant_manager.services.MenuProductService;
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

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class MenuProductControllerTest {
    @InjectMocks
    private MenuProductController menuProductController;

    @Mock
    private MenuProductService menuProductService;

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void initMvcSiMapper() {
        mvc = MockMvcBuilders.standaloneSetup(menuProductController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testGetMenuProductById() throws Exception {
        Product product = new Product(1, new ProductCategory(), "Omleta", 14.99);
        Restaurant restaurant = new Restaurant(1, "Prestorante", LocalTime.parse("07:30"), LocalTime.parse("23:00"));
        MenuProduct menuProduct = new MenuProduct(new MenuProductId(1, 1), product, restaurant,
                                                    0.0, LocalDateTime.now());

        when(menuProductService.getById(1, 1)).thenReturn(Stream.of(menuProduct).toList());

        mvc.perform(get("/menuProducts/1/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(new MenuProductId(1, 1)));
    }

    @Test
    void testGetAllMenuProducts() throws Exception {
        Product product1 = new Product(1, new ProductCategory(), "Omleta", 14.99);
        Restaurant restaurant = new Restaurant(1, "Prestorante", LocalTime.parse("07:30"), LocalTime.parse("23:00"));
        MenuProduct menuProduct1 = new MenuProduct(new MenuProductId(1, 1), product1, restaurant,
                0.0, LocalDateTime.now());

        Product product2 = new Product(1, new ProductCategory(), "Pizza Quattro Stagioni", 32.99);
        MenuProduct menuProduct2 = new MenuProduct(new MenuProductId(2, 1), product2, restaurant,
                0.0, LocalDateTime.now());

        when(menuProductService.getAllMenuProductsUsingJpa()).thenReturn(Arrays.asList(menuProduct1, menuProduct2));

        mvc.perform(get("/menuProducts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(new MenuProductId(1, 1)))
                .andExpect(jsonPath("$[1].id").value(new MenuProductId(2, 1)));
    }

//    @Test
//    void testCreateMenuProduct() throws Exception {
//        Product product = new Product(1, new ProductCategory(), "Omleta", 14.99);
//        Restaurant restaurant1 = new Restaurant(1, "Prestorante", LocalTime.parse("07:30"), LocalTime.parse("23:00"));
//        MenuProduct menuProduct = new MenuProduct(new MenuProductId(null, 1), product, restaurant1,
//                0.0, LocalDateTime.now());
//
//        Restaurant restaurant2 = new Restaurant(2, "Terasa Tic Tac", LocalTime.parse("09:30"), LocalTime.parse("21:00"));
//        MenuProduct createdMenuProduct = new MenuProduct(new MenuProductId(1, 2), product, restaurant2,
//                0.0, LocalDateTime.now());
//
//        when(menuProductService.createMenuProduct(any(MenuProduct.class))).thenReturn(createdMenuProduct);
//
//        mvc.perform(post("/menuProducts")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(menuProduct)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(new MenuProductId(1, 2)));
//    }

//    @Test
//    void testUpdateMenuProduct() throws Exception {
//        Product product = new Product(1, new ProductCategory(), "Omleta", 14.99);
//        Restaurant restaurant = new Restaurant(1, "Prestorante", LocalTime.parse("07:30"), LocalTime.parse("23:00"));
//        MenuProduct updatedMenuProduct = new MenuProduct(new MenuProductId(1, 1), product, restaurant,
//                0.0, LocalDateTime.now());
//
//        when(menuProductService.updateMenuProduct(eq(1), eq(1), any(MenuProduct.class))).thenReturn(updatedMenuProduct);
//
//        mvc.perform(put("/menuProducts/1/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatedMenuProduct)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(new MenuProductId(1, 1)));
//    }

    @Test
    void testDeleteMenuProduct() throws Exception {
        doNothing().when(menuProductService).deleteMenuProduct(1, 1);

        mvc.perform(delete("/menuProducts/1/1"))
                .andExpect(status().isNoContent());
    }
}

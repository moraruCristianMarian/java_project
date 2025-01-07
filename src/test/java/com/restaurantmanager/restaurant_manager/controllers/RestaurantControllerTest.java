package com.restaurantmanager.restaurant_manager.controllers;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.restaurantmanager.restaurant_manager.entities.Restaurant;
import com.restaurantmanager.restaurant_manager.services.RestaurantService;
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

import java.time.LocalTime;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class RestaurantControllerTest {
    @InjectMocks
    private RestaurantController restaurantController;

    @Mock
    private RestaurantService restaurantService;

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper;

    @BeforeEach
    void initMvcSiMapper() {
        mvc = MockMvcBuilders.standaloneSetup(restaurantController).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void testGetRestaurantById() throws Exception {
        Restaurant restaurant = new Restaurant(1, "Prestorante", LocalTime.parse("08:00"), LocalTime.parse("23:00"));

        when(restaurantService.getById(1)).thenReturn(Stream.of(restaurant).toList());

        mvc.perform(get("/restaurants/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Prestorante"));
    }

    @Test
    void testGetAllRestaurants() throws Exception {
        Restaurant restaurant1 = new Restaurant(1, "Prestorante", LocalTime.parse("08:00"), LocalTime.parse("23:00"));
        Restaurant restaurant2 = new Restaurant(2, "Terasa Tic Tac", LocalTime.parse("09:00"), LocalTime.parse("21:00"));

        when(restaurantService.getAllRestaurantsUsingJpa()).thenReturn(Arrays.asList(restaurant1, restaurant2));

        mvc.perform(get("/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[1].id").value(2));
    }

//    @Test
//    void testCreateRestaurant() throws Exception {
//        Restaurant restaurant = new Restaurant(null, "Prestorante", LocalTime.parse("08:00"), LocalTime.parse("23:00"));
//        Restaurant createdRestaurant = new Restaurant(1, "Terasa Tic Tac", LocalTime.parse("08:00"), LocalTime.parse("23:00"));
//
//        when(restaurantService.createRestaurant(any(Restaurant.class))).thenReturn(createdRestaurant);
//
//        mvc.perform(post("/restaurants")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(restaurant)))
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("Terasa Tic Tac"));
//    }

//    @Test
//    void testUpdateRestaurant() throws Exception {
//        Restaurant updatedRestaurant = new Restaurant(1, "Prestorante", LocalTime.parse("08:00"), LocalTime.parse("23:00"));
//
//        when(restaurantService.updateRestaurant(eq(1), any(Restaurant.class))).thenReturn(updatedRestaurant);
//
//        mvc.perform(put("/restaurants/1")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(updatedRestaurant)))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.name").value("Prestorante"));
//    }

    @Test
    void testDeleteRestaurant() throws Exception {
        doNothing().when(restaurantService).deleteRestaurant(1);

        mvc.perform(delete("/restaurants/1"))
                .andExpect(status().isNoContent());
    }
}

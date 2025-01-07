package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.Restaurant;
import com.restaurantmanager.restaurant_manager.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {
    @InjectMocks
    private RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Test
    void testCreateRestaurant() {
        Restaurant restaurant = new Restaurant(1, "Prestorante", LocalTime.parse("08:00"), LocalTime.parse("23:00"));

        Restaurant savedRestaurant = new Restaurant(1, "Prestorante", LocalTime.parse("08:00"), LocalTime.parse("23:00"));
        when(restaurantRepository.save(restaurant)).thenReturn(savedRestaurant);

        Restaurant resultRestaurant = restaurantService.createRestaurant(restaurant);

        assertNotNull(resultRestaurant);

        assertEquals(restaurant.getName(), resultRestaurant.getName());
        assertEquals(savedRestaurant.getName(), resultRestaurant.getName());
        assertEquals(savedRestaurant.getOpeningTime(), resultRestaurant.getOpeningTime());
        assertEquals(savedRestaurant.getClosingTime(), resultRestaurant.getClosingTime());

        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void testGetById() {
        Restaurant restaurant = new Restaurant(1, "Prestorante", LocalTime.parse("08:00"), LocalTime.parse("23:00"));

        when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurant));

        List<Restaurant> result = restaurantService.getById(1);

        assertNotNull(result);
        assertEquals(restaurant, result.get(0));

        verify(restaurantRepository).findById(1);
    }

    @Test
    void testGetAllRestaurantsUsingJpa() {
        Restaurant restaurant1 = new Restaurant(1, "Prestorante", LocalTime.parse("08:00"), LocalTime.parse("23:00"));
        Restaurant restaurant2 = new Restaurant(2, "Terasa Tic Tac", LocalTime.parse("07:30"), LocalTime.parse("22:00"));

        when(restaurantRepository.findAll()).thenReturn(Arrays.asList(restaurant1, restaurant2));

        List<Restaurant> result = restaurantService.getAllRestaurantsUsingJpa();

        assertNotNull(result);
        assertEquals(restaurant1, result.get(0));
        assertEquals(restaurant2, result.get(1));

        verify(restaurantRepository).findAll();
    }

    @Test
    void testUpdateRestaurant() {
        Restaurant restaurant = new Restaurant(1, "restorante", LocalTime.parse("07:59"), LocalTime.parse("23:01"));;
        Restaurant updatedRestaurant = new Restaurant(1, "Prestorante", LocalTime.parse("08:00"), LocalTime.parse("23:00"));;

        when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurant));
        when(restaurantRepository.save(restaurant)).thenReturn(updatedRestaurant);

        Restaurant result = restaurantService.updateRestaurant(1, updatedRestaurant);

        assertNotNull(result);
        assertEquals(updatedRestaurant.getName(), result.getName());
        assertEquals(updatedRestaurant.getOpeningTime(), result.getOpeningTime());
        assertEquals(updatedRestaurant.getClosingTime(), result.getClosingTime());

        verify(restaurantRepository).save(restaurant);
    }

    @Test
    void testDeleteRestaurant() {
        when(restaurantRepository.existsById(1)).thenReturn(true);

        restaurantService.deleteRestaurant(1);

        verify(restaurantRepository).deleteById(1);
    }
}

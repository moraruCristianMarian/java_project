package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.Restaurant;

import java.util.List;

public interface IRestaurantService {
    List<Restaurant> getById(Integer id);

    List<Restaurant> getAllRestaurantsUsingJpa();

    Restaurant createRestaurant(Restaurant restaurant);

    Restaurant updateRestaurant(Integer id, Restaurant updatedRestaurant);

    void deleteRestaurant(Integer id);
}

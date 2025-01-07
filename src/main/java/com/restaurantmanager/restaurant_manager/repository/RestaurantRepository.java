package com.restaurantmanager.restaurant_manager.repository;

import com.restaurantmanager.restaurant_manager.entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
//    List<Restaurant> findById(String id);
}
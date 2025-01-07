package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.Restaurant;

import com.restaurantmanager.restaurant_manager.repository.RestaurantRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService implements IRestaurantService {
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<Restaurant> getById(Integer id) {
        return this.restaurantRepository.findById(id)
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<Restaurant> getAllRestaurantsUsingJpa() {
        return this.restaurantRepository.findAll();
    }

    public Restaurant createRestaurant(Restaurant restaurant) {
        if (restaurant.getId() != null && restaurantRepository.existsById(restaurant.getId())) {
            throw new IllegalArgumentException("Restaurant already exists.");
        }
        return restaurantRepository.save(restaurant);
    }

    public Restaurant updateRestaurant(Integer id, Restaurant updatedRestaurant) {
        return restaurantRepository.findById(id).map(
                existingRestaurant -> {
                    existingRestaurant.setName(updatedRestaurant.getName());
                    existingRestaurant.setOpeningTime(updatedRestaurant.getOpeningTime());
                    existingRestaurant.setClosingTime(updatedRestaurant.getClosingTime());

                    return restaurantRepository.save(existingRestaurant);
                }
        ).orElseThrow(() -> new IllegalArgumentException("Restaurant #" + id + " not found."));
    }

    public void deleteRestaurant(Integer id) {
        if (!restaurantRepository.existsById(id)) {
            throw new IllegalArgumentException("Restaurant #" + id + " not found.");
        }
        restaurantRepository.deleteById(id);
    }
}

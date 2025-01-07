package com.restaurantmanager.restaurant_manager.repository;

import com.restaurantmanager.restaurant_manager.entities.MenuProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuProductRepository extends JpaRepository<MenuProduct, Integer> {
    Optional<MenuProduct> findByProductIdAndRestaurantId(Integer productId, Integer restaurantId);

    void deleteByProductIdAndRestaurantId(Integer productId, Integer restaurantId);

    boolean existsByProductIdAndRestaurantId(Integer productId, Integer restaurantId);
}
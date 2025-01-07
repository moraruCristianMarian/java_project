package com.restaurantmanager.restaurant_manager.repository;

import com.restaurantmanager.restaurant_manager.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
//    List<Product> findById(String id);
}
package com.restaurantmanager.restaurant_manager.repository;

import com.restaurantmanager.restaurant_manager.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
//    List<Product> findById(String id);
}
package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.Product;

import java.util.List;

public interface IProductService {
    List<Product> getById(Integer id);

    List<Product> getAllProductsUsingJpa();

    Product createProduct(Product product);

    Product updateProduct(Integer id, Product updatedProduct);

    void deleteProduct(Integer id);
}

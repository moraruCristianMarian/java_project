package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.MenuProduct;

import java.util.List;

public interface IMenuProductService {
    List<MenuProduct> getById(Integer ingredientId, Integer productId);

    List<MenuProduct> getAllMenuProductsUsingJpa();

    MenuProduct createMenuProduct(MenuProduct menuProduct);

    MenuProduct updateMenuProduct(Integer productId, Integer restaurantId, MenuProduct updatedMenuProduct);

    void deleteMenuProduct(Integer productId, Integer restaurantId);
}

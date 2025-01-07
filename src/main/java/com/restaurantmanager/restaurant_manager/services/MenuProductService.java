package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.MenuProduct;

import com.restaurantmanager.restaurant_manager.entities.MenuProductId;
import com.restaurantmanager.restaurant_manager.entities.Product;
import com.restaurantmanager.restaurant_manager.repository.MenuProductRepository;

import com.restaurantmanager.restaurant_manager.repository.ProductRepository;
import com.restaurantmanager.restaurant_manager.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class MenuProductService implements IMenuProductService {
    @Autowired
    private MenuProductRepository menuProductRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    @Override
    public List<MenuProduct> getById(Integer productId, Integer restaurantId) {
        return this.menuProductRepository.findByProductIdAndRestaurantId(productId, restaurantId)
                                         .stream()
                                         .collect(Collectors.toList());
    }

    @Override
    public List<MenuProduct> getAllMenuProductsUsingJpa() {
        return this.menuProductRepository.findAll();
    }

    public List<MenuProduct> getAllMenuProductsUsingJpaFilterCost(Double minCost, Double maxCost) {
        if (minCost == null)
            minCost = 0.0;
        if (maxCost == null)
            maxCost = 9999.0;

        Double finalMaxCost = maxCost;
        Double finalMinCost = minCost;
        return this.menuProductRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(mp -> mp.getDiscount() * mp.getProduct().getCost()))
                .filter(mp -> ((1 - mp.getDiscount()) * mp.getProduct().getCost() > finalMinCost) &&
                              ((1 - mp.getDiscount()) * mp.getProduct().getCost() < finalMaxCost))
                .collect(Collectors.toList());
    }

    public MenuProduct createMenuProduct(MenuProduct menuProduct) {
        if ((menuProduct.getId().getProductId() != null
                && (menuProduct.getId().getRestaurantId() != null)
                && (menuProductRepository.existsByProductIdAndRestaurantId(
                menuProduct.getId().getProductId(), menuProduct.getId().getRestaurantId()))
        )) {
            throw new IllegalArgumentException("MenuProduct already exists.");
        }

        try {
            menuProduct.setProduct(productRepository.findById(menuProduct.getId().getProductId()).get());
            menuProduct.setRestaurant(restaurantRepository.findById(menuProduct.getId().getRestaurantId()).get());
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Product/Restaurant not found.");
        }

        return menuProductRepository.save(menuProduct);
    }

    public MenuProduct updateMenuProduct(Integer productId, Integer restaurantId,
                                                         MenuProduct updatedMenuProduct) {
        return menuProductRepository.findByProductIdAndRestaurantId(productId, restaurantId).map(
                existingMenuProduct -> {
                    try {
                        updatedMenuProduct.setProduct(productRepository.findById(updatedMenuProduct.getId().getProductId()).get());
                        updatedMenuProduct.setRestaurant(restaurantRepository.findById(updatedMenuProduct.getId().getRestaurantId()).get());
                    } catch (NoSuchElementException e) {
                        throw new IllegalArgumentException("Product/Restaurant not found.");
                    }

                    menuProductRepository.delete(existingMenuProduct);

                    existingMenuProduct.setProduct(updatedMenuProduct.getProduct());
                    existingMenuProduct.setRestaurant(updatedMenuProduct.getRestaurant());

                    existingMenuProduct.setId(
                            new MenuProductId(
                                    updatedMenuProduct.getProduct().getId(),
                                    updatedMenuProduct.getRestaurant().getId()
                            )
                    );
                    existingMenuProduct.setDiscount(updatedMenuProduct.getDiscount());
                    existingMenuProduct.setPromotionEndDate(updatedMenuProduct.getPromotionEndDate());

                    return menuProductRepository.save(existingMenuProduct);
                }
        ).orElseThrow(() -> new IllegalArgumentException("MenuProduct #" + productId + "#" + restaurantId + " not found."));
    }

    public void deleteMenuProduct(Integer productId, Integer restaurantId) {
        menuProductRepository.findByProductIdAndRestaurantId(productId, restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("MenuProduct #" + productId + "#" + restaurantId + " not found."));

        menuProductRepository.findByProductIdAndRestaurantId(productId, restaurantId).map(
                existingMenuProduct -> {
                    menuProductRepository.delete(existingMenuProduct);
                    return null;
                }
        );
    }
}

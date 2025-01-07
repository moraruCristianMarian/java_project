package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.IngredientInProduct;

import com.restaurantmanager.restaurant_manager.entities.IngredientInProductId;
import com.restaurantmanager.restaurant_manager.repository.IngredientInProductRepository;

import com.restaurantmanager.restaurant_manager.repository.IngredientRepository;
import com.restaurantmanager.restaurant_manager.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class IngredientInProductService implements IIngredientInProductService {
    @Autowired
    private IngredientInProductRepository ingredientInProductRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<IngredientInProduct> getById(Integer ingredientId, Integer productId) {
        return this.ingredientInProductRepository.findByIngredientIdAndProductId(ingredientId, productId)
                                                 .stream()
                                                 .collect(Collectors.toList());
    }

    @Override
    public List<IngredientInProduct> getAllIngredientInProductsUsingJpa() {
        return this.ingredientInProductRepository.findAll();
    }

    public IngredientInProduct createIngredientInProduct(IngredientInProduct ingredientInProduct) {
        if ((ingredientInProduct.getId().getIngredientId() != null
                && (ingredientInProduct.getId().getProductId() != null)
                && (ingredientInProductRepository.existsByIngredientIdAndProductId(
                        ingredientInProduct.getId().getIngredientId(), ingredientInProduct.getId().getProductId()))
            )) {
            throw new IllegalArgumentException("IngredientInProduct already exists.");
        }

        try {
            ingredientInProduct.setIngredient(ingredientRepository.findById(ingredientInProduct.getId().getIngredientId()).get());
            ingredientInProduct.setProduct(productRepository.findById(ingredientInProduct.getId().getProductId()).get());
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Ingredient/Product not found.");
        }

        return ingredientInProductRepository.save(ingredientInProduct);
    }

    public IngredientInProduct updateIngredientInProduct(Integer ingredientId, Integer productId,
                                                         IngredientInProduct updatedIngredientInProduct) {
        return ingredientInProductRepository.findByIngredientIdAndProductId(ingredientId, productId).map(
                existingIngredientInProduct -> {
                    try {
                        updatedIngredientInProduct.setIngredient(ingredientRepository.findById(updatedIngredientInProduct.getId().getIngredientId()).get());
                        updatedIngredientInProduct.setProduct(productRepository.findById(updatedIngredientInProduct.getId().getProductId()).get());
                    } catch (NoSuchElementException e) {
                        throw new IllegalArgumentException("Ingredient/Product not found.");
                    }

                    ingredientInProductRepository.delete(existingIngredientInProduct);

                    existingIngredientInProduct.setIngredient(updatedIngredientInProduct.getIngredient());
                    existingIngredientInProduct.setProduct(updatedIngredientInProduct.getProduct());

                    existingIngredientInProduct.setId(
                        new IngredientInProductId(
                            updatedIngredientInProduct.getIngredient().getId(),
                            updatedIngredientInProduct.getProduct().getId()
                        )
                    );

                    return ingredientInProductRepository.save(existingIngredientInProduct);
                }
        ).orElseThrow(() -> new IllegalArgumentException("IngredientInProduct #" + ingredientId + "#" + productId + " not found."));
    }

    public void deleteIngredientInProduct(Integer ingredientId, Integer productId) {
        ingredientInProductRepository.findByIngredientIdAndProductId(ingredientId, productId)
                .orElseThrow(() -> new IllegalArgumentException("IngredientInProduct #" + ingredientId + "#" + productId + " not found."));

        ingredientInProductRepository.findByIngredientIdAndProductId(ingredientId, productId).map(
                existingIngredientInProduct -> {
                    ingredientInProductRepository.delete(existingIngredientInProduct);
                    return null;
                }
        );
    }
}

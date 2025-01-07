package com.restaurantmanager.restaurant_manager.json;

import javax.validation.constraints.NotBlank;

public class IngredientInProduct {

    @NotBlank
    private Integer ingredientId;
    @NotBlank
    private Integer productId;

    public IngredientInProduct() {
    }

    public IngredientInProduct(Integer ingredientId, Integer productId) {
        this.ingredientId = ingredientId;
        this.productId = productId;
    }

    public String getIngredientId() {
        return ingredientId.toString();
    }

    public String getProductId() {
        return productId.toString();
    }

    @Override
    public String toString() {
        return "IngredientInProduct{" +
                "ingredientId='" + ingredientId + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}
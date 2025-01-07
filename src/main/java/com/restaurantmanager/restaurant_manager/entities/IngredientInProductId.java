package com.restaurantmanager.restaurant_manager.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class IngredientInProductId implements Serializable {
    private Integer ingredientId;
    private Integer productId;

    public IngredientInProductId() {
    }

    public IngredientInProductId(Integer ingredientId, Integer productId) {
        this.ingredientId = ingredientId;
        this.productId = productId;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IngredientInProductId other = (IngredientInProductId) o;
        return Objects.equals(ingredientId, other.ingredientId) &&
               Objects.equals(productId, other.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredientId, productId);
    }
}

package com.restaurantmanager.restaurant_manager.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ingredient_in_products")
public class IngredientInProduct {
    @EmbeddedId
    private IngredientInProductId id;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    public IngredientInProduct() {
    }
    public IngredientInProduct(IngredientInProductId id) {
        this.id = id;
    }
    public IngredientInProduct(IngredientInProductId id, Ingredient ingredient, Product product) {
        this.id = id;
        this.ingredient = ingredient;
        this.product = product;
    }

    public IngredientInProductId getId() {
        return id;
    }

    public void setId(IngredientInProductId id) {
        this.id = id;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}



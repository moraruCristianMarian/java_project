package com.restaurantmanager.restaurant_manager.json;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ProductCategory {

    @NotBlank
    private Integer id;
    @NotBlank
    private String name;

    public ProductCategory() {
    }

    public ProductCategory(Integer id, Integer productCategoryCategoryId, String name, Double cost) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
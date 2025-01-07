package com.restaurantmanager.restaurant_manager.json;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Product {

    @NotBlank
    private Integer id;
    @NotBlank
    private Integer productCategoryId;
    @NotBlank
    private String name;
    @NotBlank
    @Min(0)
    private Double cost;

    public Product() {
    }

    public Product(Integer id, Integer productCategoryId, String name, Double cost) {
        this.id = id;
        this.productCategoryId = productCategoryId;
        this.name = name;
        this.cost = cost;
    }

    public String getId() {
        return id.toString();
    }

    public String getProductCategoryId() {
        return productCategoryId.toString();
    }

    public String getName() {
        return name;
    }

    public String getCost() {
        return cost.toString();
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", product_category_id='" + productCategoryId + '\'' +
                ", name='" + name + '\'' +
                ", cost='" + cost + '\'' +
                '}';
    }
}
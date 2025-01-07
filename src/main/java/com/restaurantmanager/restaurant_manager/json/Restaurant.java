package com.restaurantmanager.restaurant_manager.json;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import java.time.LocalTime;

public class Restaurant {

    @NotBlank
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private LocalTime openingTime;
    @NotBlank
    private LocalTime closingTime;

    public Restaurant() {
    }

    public Restaurant(Integer id, Integer restaurantCategoryId, String name, LocalTime openingTime, LocalTime closingTime) {
        this.id = id;
        this.name = name;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return name;
    }

    public String getOpeningTime() {
        return openingTime.toString();
    }

    public String getClosingTime() {
        return closingTime.toString();
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", openingTime='" + openingTime + '\'' +
                ", closingTime='" + closingTime + '\'' +
                '}';
    }
}
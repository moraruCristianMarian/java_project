package com.restaurantmanager.restaurant_manager.json;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Ingredient {

    @NotBlank
    private Integer id;
    @NotBlank
    private String name;
    @NotBlank
    private Boolean isMeat;
    @NotBlank
    private Boolean isDairy;
    @NotBlank
    private Boolean isGluten;

    public Ingredient() {
    }

    public Ingredient(Integer id, Integer ingredientCategoryId, String name, Boolean isMeat, Boolean isDairy, Boolean isGluten) {
        this.id = id;
        this.name = name;
        this.isMeat = isMeat;
        this.isDairy = isDairy;
        this.isGluten = isGluten;
    }

    public String getId() {
        return id.toString();
    }

    public String getName() {
        return name;
    }

    public String getIsMeat() {
        return isMeat.toString();
    }

    public String getIsDairy() {
        return isDairy.toString();
    }

    public String getIsGluten() {
        return isGluten.toString();
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", isMeat='" + isMeat + '\'' +
                ", isDairy='" + isDairy + '\'' +
                ", isGluten='" + isGluten + '\'' +
                '}';
    }
}
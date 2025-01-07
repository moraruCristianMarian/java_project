package com.restaurantmanager.restaurant_manager.json;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class MenuProduct {

    @NotBlank
    private Integer productId;
    @NotBlank
    private Integer restaurantId;
    @NotBlank
    private Double discount;
    @NotBlank
    private LocalDateTime promotionEndDate;

    public MenuProduct() {
    }

    public MenuProduct(Integer productId, Integer restaurantId, Double discount, LocalDateTime promotionEndDate) {
        this.productId = productId;
        this.restaurantId = restaurantId;
        this.discount = discount;
        this.promotionEndDate = promotionEndDate;
    }

    public String getRestaurantId() {
        return restaurantId.toString();
    }

    public String getProductId() {
        return productId.toString();
    }

    public String getDiscount() {
        return discount.toString();
    }

    public String getPromotionEndDate() {
        return promotionEndDate.toString();
    }

    @Override
    public String toString() {
        return "MenuProduct{" +
                "productId='" + productId + '\'' +
                ", restaurantId='" + restaurantId + '\'' +
                ", discount='" + discount + '\'' +
                ", promotionEndDate='" + promotionEndDate + '\'' +
                '}';
    }
}
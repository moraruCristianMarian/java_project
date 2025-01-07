package com.restaurantmanager.restaurant_manager.entities;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class MenuProductId implements Serializable {
    private Integer productId;
    private Integer restaurantId;

    public MenuProductId() {
    }

    public MenuProductId(Integer productId, Integer restaurantId) {
        this.productId = productId;
        this.restaurantId = restaurantId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MenuProductId other = (MenuProductId) o;
        return Objects.equals(productId, other.productId) &&
                Objects.equals(restaurantId, other.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, restaurantId);
    }
}

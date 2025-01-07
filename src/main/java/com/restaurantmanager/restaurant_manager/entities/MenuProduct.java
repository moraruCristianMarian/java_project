package com.restaurantmanager.restaurant_manager.entities;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "menu_products")
public class MenuProduct {
    @EmbeddedId
    private MenuProductId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("restaurantId")
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "discount")
    @Min(0)
    @Max(1)
    private Double discount;

    @Column(name = "promotion_end_date")
    private LocalDateTime promotionEndDate;

    public MenuProduct() {
    }

    public MenuProduct(MenuProductId id, Product product, Restaurant restaurant, Double discount, LocalDateTime promotionEndDate) {
        this.id = id;
        this.product = product;
        this.restaurant = restaurant;
        this.discount = discount;
        this.promotionEndDate = promotionEndDate;
    }

    public MenuProductId getId() {
        return id;
    }

    public void setId(MenuProductId id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public LocalDateTime getPromotionEndDate() {
        return promotionEndDate;
    }

    public void setPromotionEndDate(LocalDateTime promotionEndDate) {
        this.promotionEndDate = promotionEndDate;
    }
}



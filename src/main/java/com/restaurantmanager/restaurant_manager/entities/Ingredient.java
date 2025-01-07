package com.restaurantmanager.restaurant_manager.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ingredients")
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    @Size(min = 2, max = 50)
    private String name;

    @Column(name = "is_meat")
    private Boolean isMeat;
    @Column(name = "is_dairy")
    private Boolean isDairy;
    @Column(name = "is_gluten")
    private Boolean isGluten;

    public Ingredient() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsMeat() {
        return isMeat;
    }

    public void setIsMeat(Boolean isMeat) {
        this.isMeat = isMeat;
    }

    public Boolean getIsDairy() {
        return isDairy;
    }

    public void setIsDairy(Boolean isDairy) {
        this.isDairy = isDairy;
    }

    public Boolean getIsGluten() {
        return isGluten;
    }

    public void setIsGluten(Boolean isGluten) {
        this.isGluten = isGluten;
    }
}

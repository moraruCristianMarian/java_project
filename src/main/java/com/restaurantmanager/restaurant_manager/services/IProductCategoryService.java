package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.ProductCategory;

import java.util.List;

public interface IProductCategoryService {
    List<ProductCategory> getById(Integer id);

    List<ProductCategory> getAllProductCategorysUsingJpa();

    ProductCategory createProductCategory(ProductCategory product);

    ProductCategory updateProductCategory(Integer id, ProductCategory updatedProductCategory);

    void deleteProductCategory(Integer id);
}

package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.ProductCategory;

import com.restaurantmanager.restaurant_manager.repository.ProductCategoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryService implements IProductCategoryService {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public List<ProductCategory> getById(Integer id) {
        return this.productCategoryRepository.findById(id)
                .stream()
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductCategory> getAllProductCategoriesUsingJpa() {
        return this.productCategoryRepository.findAll();
    }

    public ProductCategory createProductCategory(ProductCategory productCategory) {
        if (productCategory.getId() != null && productCategoryRepository.existsById(productCategory.getId())) {
            throw new IllegalArgumentException("ProductCategory already exists.");
        }
        return productCategoryRepository.save(productCategory);
    }

    public ProductCategory updateProductCategory(Integer id, ProductCategory updatedProductCategory) {
        return productCategoryRepository.findById(id).map(
                existingProductCategory -> {
                    existingProductCategory.setName(updatedProductCategory.getName());

                    return productCategoryRepository.save(existingProductCategory);
                }
        ).orElseThrow(() -> new IllegalArgumentException("ProductCategory #" + id + " not found."));
    }

    public void deleteProductCategory(Integer id) {
        if (!productCategoryRepository.existsById(id)) {
            throw new IllegalArgumentException("ProductCategory #" + id + " not found.");
        }
        productCategoryRepository.deleteById(id);
    }
}

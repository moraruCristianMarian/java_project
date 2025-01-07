package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.Product;

import com.restaurantmanager.restaurant_manager.repository.IngredientInProductRepository;
import com.restaurantmanager.restaurant_manager.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private IngredientInProductRepository ingredientInProductRepository;

    @Override
    public List<Product> getById(Integer id) {
        return this.productRepository.findById(id)
                                     .stream()
                                     .collect(Collectors.toList());
    }

    @Override
    public List<Product> getAllProductsUsingJpa() {
        return this.productRepository.findAll();
    }

    public List<Product> getAllProductsUsingJpaByCost() {
        return this.productRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(Product::getCost))
                .collect(Collectors.toList());
    }

    public List<Product> getProductsFilterIngredient(Integer ingredientId) {
        List<Product> allProducts = productRepository.findAll();

        List<Integer> productIdsWithIngredient = this.ingredientInProductRepository.findAll()
                                                     .stream()
                                                     .filter(ingredientInProduct ->
                                                             ingredientInProduct.getIngredient().getId().equals(ingredientId))
                                                     .map(ingredientInProduct ->
                                                          ingredientInProduct.getProduct().getId())
                                                     .toList();

        return allProducts.stream()
                .filter(product -> !productIdsWithIngredient.contains(product.getId()))
                .collect(Collectors.toList());
    }

    public Product createProduct(Product product) {
        if (product.getId() != null && productRepository.existsById(product.getId())) {
            throw new IllegalArgumentException("Product already exists.");
        }
        return productRepository.save(product);
    }

    public Product updateProduct(Integer id, Product updatedProduct) {
        return productRepository.findById(id).map(
            existingProduct -> {
                existingProduct.setName(updatedProduct.getName());
                existingProduct.setCost(updatedProduct.getCost());
                existingProduct.setProductCategory(updatedProduct.getProductCategory());

                return productRepository.save(existingProduct);
            }
        ).orElseThrow(() -> new IllegalArgumentException("Product #" + id + " not found."));
    }

    public void deleteProduct(Integer id) {
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("Product #" + id + " not found.");
        }
        productRepository.deleteById(id);
    }
}

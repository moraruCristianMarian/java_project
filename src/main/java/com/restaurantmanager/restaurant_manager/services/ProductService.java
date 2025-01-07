package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.Product;

import com.restaurantmanager.restaurant_manager.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductRepository productRepository;

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

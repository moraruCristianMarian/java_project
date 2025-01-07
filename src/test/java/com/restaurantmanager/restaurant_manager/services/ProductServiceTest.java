package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.Product;
import com.restaurantmanager.restaurant_manager.entities.ProductCategory;
import com.restaurantmanager.restaurant_manager.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Test
    void testCreateProduct() {
        Product product = new Product(1, new ProductCategory(), "Cartofi gratinati", 14.99);

        Product savedProduct = new Product(1, new ProductCategory(), "Cartofi gratinati", 14.99);
        when(productRepository.save(product)).thenReturn(savedProduct);

        Product resultProduct = productService.createProduct(product);

        assertNotNull(resultProduct);

        assertEquals(product.getName(), resultProduct.getName());
        assertEquals(savedProduct.getName(), resultProduct.getName());

        verify(productRepository).save(product);
    }

    @Test
    void testGetById() {
        Product product = new Product(1, new ProductCategory(), "Gris cu lapte", 14.99);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        List<Product> result = productService.getById(1);

        assertNotNull(result);
        assertEquals(product, result.get(0));

        verify(productRepository).findById(1);
    }

    @Test
    void testGetAllProductsUsingJpa() {
        Product product1 = new Product(1, new ProductCategory(), "Placinta cu dovleac", 18.99);
        Product product2 = new Product(2, new ProductCategory(), "Pizza Quattro Stagioni", 29.99);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));

        List<Product> result = productService.getAllProductsUsingJpa();

        assertNotNull(result);
        assertEquals(product1, result.get(0));
        assertEquals(product2, result.get(1));

        verify(productRepository).findAll();
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product(1, new ProductCategory(), "Cartofi gratinati", 14.99);
        Product updatedProduct = new Product(1, new ProductCategory(), "Ardei umpluti cu carne de pui", 34.99);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(updatedProduct);

        Product result = productService.updateProduct(1, updatedProduct);

        assertNotNull(result);
        assertEquals(updatedProduct.getName(), result.getName());
        assertEquals(updatedProduct.getCost(), result.getCost());

        verify(productRepository).save(product);
    }

    @Test
    void testDeleteProduct() {
        when(productRepository.existsById(1)).thenReturn(true);

        productService.deleteProduct(1);

        verify(productRepository).deleteById(1);
    }
}

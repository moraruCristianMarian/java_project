package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.*;
import com.restaurantmanager.restaurant_manager.repository.MenuProductRepository;
import com.restaurantmanager.restaurant_manager.repository.RestaurantRepository;
import com.restaurantmanager.restaurant_manager.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MenuProductServiceTest {
    @InjectMocks
    private MenuProductService menuProductService;

    @Mock
    private MenuProductRepository menuProductRepository;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private ProductRepository productRepository;

    @Test
    void testCreateMenuProduct() {
        Restaurant restaurant = new Restaurant(1, "Prestorante", LocalTime.parse("08:00"), LocalTime.parse("23:00"));
        Product product = new Product(1, new ProductCategory(), "Cartofi la cuptor", 13.99);
        MenuProduct menuProduct = new MenuProduct(
                new MenuProductId(product.getId(), restaurant.getId()), product, restaurant, 0.0, LocalDateTime.now());

        MenuProduct savedMenuProduct = new MenuProduct(
                new MenuProductId(product.getId(), restaurant.getId()), product, restaurant, 0.0, LocalDateTime.now());
        when(menuProductRepository.save(menuProduct)).thenReturn(savedMenuProduct);

        when(productRepository.findById(1)).thenReturn(Optional.of(product));
        when(restaurantRepository.findById(1)).thenReturn(Optional.of(restaurant));

        MenuProduct resultMenuProduct = menuProductService.createMenuProduct(menuProduct);

        assertNotNull(resultMenuProduct);

        assertEquals(menuProduct.getId(), resultMenuProduct.getId());
        assertEquals(savedMenuProduct.getId(), resultMenuProduct.getId());

        verify(menuProductRepository).save(menuProduct);
    }

    @Test
    void testGetById() {
        Restaurant restaurant = new Restaurant(1, "Prestorante", LocalTime.parse("08:00"), LocalTime.parse("23:00"));
        Product product = new Product(1, new ProductCategory(), "Cartofi la cuptor", 13.99);
        MenuProduct menuProduct = new MenuProduct(
                new MenuProductId(product.getId(), restaurant.getId()), product, restaurant, 0.0, LocalDateTime.now());

        when(menuProductRepository.findByProductIdAndRestaurantId(1, 1)).thenReturn(Optional.of(menuProduct));

        List<MenuProduct> result = menuProductService.getById(restaurant.getId(), product.getId());

        assertNotNull(result);
        assertEquals(menuProduct, result.get(0));

        verify(menuProductRepository).findByProductIdAndRestaurantId(restaurant.getId(), product.getId());
    }

    @Test
    void testGetAllMenuProductsUsingJpa() {
        Restaurant restaurant = new Restaurant(1, "Prestorante", LocalTime.parse("08:00"), LocalTime.parse("23:00"));
        Product product1 = new Product(1, new ProductCategory(), "Cartofi la cuptor", 13.99);
        MenuProduct menuProduct1 = new MenuProduct(
                new MenuProductId(product1.getId(), restaurant.getId()), product1, restaurant, 0.0, LocalDateTime.now());

        Product product2 = new Product(1, new ProductCategory(), "Cartofi prajiti", 11.99);
        MenuProduct menuProduct2 = new MenuProduct(
                new MenuProductId(product2.getId(), restaurant.getId()), product2, restaurant, 0.0, LocalDateTime.now());


        when(menuProductRepository.findAll()).thenReturn(Arrays.asList(menuProduct1, menuProduct2));

        List<MenuProduct> result = menuProductService.getAllMenuProductsUsingJpa();

        assertNotNull(result);
        assertEquals(menuProduct1, result.get(0));
        assertEquals(menuProduct2, result.get(1));

        verify(menuProductRepository).findAll();
    }

    @Test
    void testUpdateMenuProduct() {
        Restaurant restaurant = new Restaurant(1, "Prestorante", LocalTime.parse("08:00"), LocalTime.parse("23:00"));
        Product product1 = new Product(1, new ProductCategory(), "Salata vegetariana", 18.99);
        MenuProduct menuProduct = new MenuProduct(
                new MenuProductId(product1.getId(), restaurant.getId()), product1, restaurant, 0.0, LocalDateTime.now());

        Product product2 = new Product(2, new ProductCategory(), "Salata cezar", 21.99);
        MenuProduct updatedMenuProduct = new MenuProduct(
                new MenuProductId(product2.getId(), restaurant.getId()), product2, restaurant, 0.0, LocalDateTime.now());

        when(menuProductRepository.findByProductIdAndRestaurantId(product1.getId(), restaurant.getId()))
                .thenReturn(Optional.of(menuProduct));

        when(restaurantRepository.findById(restaurant.getId()))
                .thenReturn(Optional.of(restaurant));

        when(productRepository.findById(product2.getId()))
                .thenReturn(Optional.of(product2));

        when(menuProductRepository.save(any(MenuProduct.class)))
                .thenReturn(updatedMenuProduct);

        MenuProduct result = menuProductService.updateMenuProduct(
                restaurant.getId(), product1.getId(), updatedMenuProduct);

        assertNotNull(result);
        assertEquals(restaurant.getId(), result.getRestaurant().getId());
        assertEquals(product2.getId(), result.getProduct().getId());

        verify(menuProductRepository).findByProductIdAndRestaurantId(product1.getId(), restaurant.getId());
        verify(restaurantRepository).findById(restaurant.getId());
        verify(productRepository).findById(product2.getId());
        verify(menuProductRepository).delete(menuProduct);
        verify(menuProductRepository).save(any(MenuProduct.class));
    }

    @Test
    void testDeleteMenuProduct() {
        Restaurant restaurant = new Restaurant(1, "Prestorante", LocalTime.parse("08:00"), LocalTime.parse("23:00"));
        Product product = new Product(1, new ProductCategory(), "Salata cezar", 21.99);
        MenuProduct menuProduct = new MenuProduct(
                new MenuProductId(product.getId(), restaurant.getId()), product, restaurant, 0.0, LocalDateTime.now());

        when(menuProductRepository.findByProductIdAndRestaurantId(product.getId(), restaurant.getId()))
                .thenReturn(Optional.of(menuProduct));

        menuProductService.deleteMenuProduct(product.getId(), restaurant.getId());

        verify(menuProductRepository).delete(menuProduct);
    }
}

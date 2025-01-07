package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.*;
import com.restaurantmanager.restaurant_manager.repository.IngredientInProductRepository;
import com.restaurantmanager.restaurant_manager.repository.IngredientRepository;
import com.restaurantmanager.restaurant_manager.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientInProductServiceTest {
    @InjectMocks
    private IngredientInProductService ingredientInProductService;

    @Mock
    private IngredientInProductRepository ingredientInProductRepository;
    @Mock
    private IngredientRepository ingredientRepository;
    @Mock
    private ProductRepository productRepository;

    @Test
    void testCreateIngredientInProduct() {
        Ingredient ingredient = new Ingredient(1, "Cartofi", false, false, false);
        Product product = new Product(1, new ProductCategory(), "Cartofi la cuptor", 13.99);
        IngredientInProduct ingredientInProduct = new IngredientInProduct(
                new IngredientInProductId(ingredient.getId(), product.getId()), ingredient, product);

        IngredientInProduct savedIngredientInProduct = new IngredientInProduct(
                new IngredientInProductId(1, 1));
        when(ingredientInProductRepository.save(ingredientInProduct)).thenReturn(savedIngredientInProduct);

        when(ingredientRepository.findById(1)).thenReturn(Optional.of(ingredient));
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        IngredientInProduct resultIngredientInProduct = ingredientInProductService.createIngredientInProduct(ingredientInProduct);

        assertNotNull(resultIngredientInProduct);

        assertEquals(ingredientInProduct.getId(), resultIngredientInProduct.getId());
        assertEquals(savedIngredientInProduct.getId(), resultIngredientInProduct.getId());

        verify(ingredientInProductRepository).save(ingredientInProduct);
    }

    @Test
    void testGetById() {
        Ingredient ingredient = new Ingredient(1, "Cartofi", false, false, false);
        Product product = new Product(1, new ProductCategory(), "Cartofi la cuptor", 13.99);
        IngredientInProduct ingredientInProduct = new IngredientInProduct(
                new IngredientInProductId(ingredient.getId(), product.getId()), ingredient, product);

        when(ingredientInProductRepository.findByIngredientIdAndProductId(1, 1)).thenReturn(Optional.of(ingredientInProduct));

        List<IngredientInProduct> result = ingredientInProductService.getById(ingredient.getId(), product.getId());

        assertNotNull(result);
        assertEquals(ingredientInProduct, result.get(0));

        verify(ingredientInProductRepository).findByIngredientIdAndProductId(ingredient.getId(), product.getId());
    }

    @Test
    void testGetAllIngredientInProductsUsingJpa() {
        Ingredient ingredient = new Ingredient(1, "Cartofi", false, false, false);
        Product product1 = new Product(1, new ProductCategory(), "Cartofi la cuptor", 13.99);
        IngredientInProduct ingredientInProduct1 = new IngredientInProduct(
                new IngredientInProductId(ingredient.getId(), product1.getId()), ingredient, product1);

        Product product2 = new Product(1, new ProductCategory(), "Cartofi prajiti", 11.99);
        IngredientInProduct ingredientInProduct2 = new IngredientInProduct(
                new IngredientInProductId(ingredient.getId(), product2.getId()), ingredient, product2);


        when(ingredientInProductRepository.findAll()).thenReturn(Arrays.asList(ingredientInProduct1, ingredientInProduct2));

        List<IngredientInProduct> result = ingredientInProductService.getAllIngredientInProductsUsingJpa();

        assertNotNull(result);
        assertEquals(ingredientInProduct1, result.get(0));
        assertEquals(ingredientInProduct2, result.get(1));

        verify(ingredientInProductRepository).findAll();
    }

    @Test
    void testUpdateIngredientInProduct() {
        Ingredient ingredient = new Ingredient(1, "Carne de pui", true, false, false);
        Product product1 = new Product(1, new ProductCategory(), "Salata vegetariana", 18.99);
        IngredientInProduct ingredientInProduct = new IngredientInProduct(
                new IngredientInProductId(ingredient.getId(), product1.getId()), ingredient, product1);

        Product product2 = new Product(2, new ProductCategory(), "Salata cezar", 21.99);
        IngredientInProduct updatedIngredientInProduct = new IngredientInProduct(
                new IngredientInProductId(ingredient.getId(), product2.getId()), ingredient, product2);

        when(ingredientInProductRepository.findByIngredientIdAndProductId(ingredient.getId(), product1.getId()))
                .thenReturn(Optional.of(ingredientInProduct));

        when(ingredientRepository.findById(ingredient.getId()))
                .thenReturn(Optional.of(ingredient));

        when(productRepository.findById(product2.getId()))
                .thenReturn(Optional.of(product2));

        when(ingredientInProductRepository.save(any(IngredientInProduct.class)))
                .thenReturn(updatedIngredientInProduct);

        IngredientInProduct result = ingredientInProductService.updateIngredientInProduct(
                ingredient.getId(), product1.getId(), updatedIngredientInProduct);

        assertNotNull(result);
        assertEquals(ingredient.getId(), result.getIngredient().getId());
        assertEquals(product2.getId(), result.getProduct().getId());

        verify(ingredientInProductRepository).findByIngredientIdAndProductId(ingredient.getId(), product1.getId());
        verify(ingredientRepository).findById(ingredient.getId());
        verify(productRepository).findById(product2.getId());
        verify(ingredientInProductRepository).delete(ingredientInProduct);
        verify(ingredientInProductRepository).save(any(IngredientInProduct.class));
    }

    @Test
    void testDeleteIngredientInProduct() {
        Ingredient ingredient = new Ingredient(1, "Carne de pui", true, false, false);
        Product product = new Product(1, new ProductCategory(), "Salata cezar", 21.99);
        IngredientInProduct ingredientInProduct = new IngredientInProduct(
                new IngredientInProductId(ingredient.getId(), product.getId()), ingredient, product);

        when(ingredientInProductRepository.findByIngredientIdAndProductId(ingredient.getId(), product.getId()))
                .thenReturn(Optional.of(ingredientInProduct));

        ingredientInProductService.deleteIngredientInProduct(ingredient.getId(), product.getId());

        verify(ingredientInProductRepository).delete(ingredientInProduct);
    }
}

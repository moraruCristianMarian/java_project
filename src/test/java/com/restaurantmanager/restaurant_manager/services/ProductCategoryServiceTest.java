package com.restaurantmanager.restaurant_manager.services;

import com.restaurantmanager.restaurant_manager.entities.ProductCategory;
import com.restaurantmanager.restaurant_manager.repository.ProductCategoryRepository;
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
class ProductCategoryServiceTest {
    @InjectMocks
    private ProductCategoryService productCategoryService;

    @Mock
    private ProductCategoryRepository productCategoryRepository;

    @Test
    void testCreateProductCategory() {
        ProductCategory productCategory = new ProductCategory(1, "Mic dejun");

        ProductCategory savedProductCategory = new ProductCategory(1, "Mic dejun");
        when(productCategoryRepository.save(productCategory)).thenReturn(savedProductCategory);

        ProductCategory resultProductCategory = productCategoryService.createProductCategory(productCategory);

        assertNotNull(resultProductCategory);

        assertEquals(productCategory.getName(), resultProductCategory.getName());
        assertEquals(savedProductCategory.getName(), resultProductCategory.getName());

        verify(productCategoryRepository).save(productCategory);
    }

    @Test
    void testGetById() {
        ProductCategory productCategory = new ProductCategory(1, "Aperitive");

        when(productCategoryRepository.findById(1)).thenReturn(Optional.of(productCategory));

        List<ProductCategory> result = productCategoryService.getById(1);

        assertNotNull(result);
        assertEquals(productCategory, result.get(0));

        verify(productCategoryRepository).findById(1);
    }

    @Test
    void testGetAllProductCategoriesUsingJpa() {
        ProductCategory productCategory1 = new ProductCategory(1, "Desert");
        ProductCategory productCategory2 = new ProductCategory(2, "Garnituri");

        when(productCategoryRepository.findAll()).thenReturn(Arrays.asList(productCategory1, productCategory2));

        List<ProductCategory> result = productCategoryService.getAllProductCategoriesUsingJpa();

        assertNotNull(result);
        assertEquals(productCategory1, result.get(0));
        assertEquals(productCategory2, result.get(1));

        verify(productCategoryRepository).findAll();
    }

    @Test
    void testUpdateProductCategory() {
        ProductCategory productCategory = new ProductCategory(1, "Mic dejnu");
        ProductCategory updatedProductCategory = new ProductCategory(1, "Mic dejun");

        when(productCategoryRepository.findById(1)).thenReturn(Optional.of(productCategory));
        when(productCategoryRepository.save(productCategory)).thenReturn(updatedProductCategory);

        ProductCategory result = productCategoryService.updateProductCategory(1, updatedProductCategory);

        assertNotNull(result);
        assertEquals(updatedProductCategory.getName(), result.getName());

        verify(productCategoryRepository).save(productCategory);
    }

    @Test
    void testDeleteProductCategory() {
        when(productCategoryRepository.existsById(1)).thenReturn(true);

        productCategoryService.deleteProductCategory(1);

        verify(productCategoryRepository).deleteById(1);
    }
}

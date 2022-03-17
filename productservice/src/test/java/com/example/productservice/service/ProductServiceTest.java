package com.example.productservice.service;

import com.example.productservice.domain.Product;
import com.example.productservice.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void givenProductIdThatExistShouldReturnProduct() {
        Product mockProduct = new Product(1L, "Product Name", 10L, 1L);
        when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));

        Optional<Product> returnedProduct = productService.findById(1L);

        assertThat(returnedProduct.isPresent()).isTrue();
        assertThat(returnedProduct.get()).isEqualTo(mockProduct);
    }

    @Test
    void givenProductIdThatDoesNotExitsShouldReturnNull() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThat(productService.findById(1L)).isEqualTo(Optional.empty());
    }

    @Test
    void shouldReturnAllProducts() {
        Product mockProduct = new Product(1L, "Product Name", 10L, 1L);
        Product mockProduct2 = new Product(2L, "Product Name 2", 15L, 3L);
        when(productRepository.findAll()).thenReturn(Arrays.asList(mockProduct, mockProduct2));
        Optional<List<Product>> products = productService.findAll();

        assertThat(products.isPresent()).isTrue();
        assertThat(products.get()).hasSize(2);
    }

    @Test
    void shouldSaveProduct() {
        Product mockProduct = new Product(1L, "Product Name", 10L, 1L);
        when(productRepository.save(any())).thenReturn(mockProduct);

        Product returnedProduct = productService.save(mockProduct);

        assertThat(returnedProduct).isNotNull();
        assertThat(returnedProduct.getVersion()).isEqualTo(1L);
    }

}
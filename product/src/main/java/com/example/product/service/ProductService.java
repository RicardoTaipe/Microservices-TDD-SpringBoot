package com.example.product.service;

import com.example.product.entity.Product;

import java.util.List;

public interface ProductService {
    Product findProductById(Long id);

    List<Product> getProducts();

    void update(Product product);

    Product save(Product product);

    void delete(Long id);
}

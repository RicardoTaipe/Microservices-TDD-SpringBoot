package com.example.productservice.service;

import com.example.productservice.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    Optional<Product> findById(Long id);

    Optional<List<Product>> findAll();

    void update(Product product);

    Product save(Product product);

    void delete(Long id);
}

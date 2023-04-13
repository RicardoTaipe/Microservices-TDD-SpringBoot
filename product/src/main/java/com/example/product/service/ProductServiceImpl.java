package com.example.product.service;

import com.example.product.entity.Product;
import com.example.product.exception.ResourceNotFoundException;
import com.example.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product not found for this id ::" + id)
        );
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public void update(Product product) {
        productRepository.findById(product.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Product not found for this id ::" + product.getId())
        );
        productRepository.save(product);
    }

    @Override
    public Product save(Product product) {
        // Set the product version to 1 as we're adding a new product to the database
        product.setVersion(1);
        return productRepository.save(product);
    }

    @Override
    public void delete(Long id) {
        productRepository.findById(id)
                .orElseThrow(
                        () -> new ResourceNotFoundException("Product not found for this id ::" + id)
                );
        productRepository.deleteById(id);
    }
}

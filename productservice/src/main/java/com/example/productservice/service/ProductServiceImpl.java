package com.example.productservice.service;

import com.example.productservice.domain.Product;
import com.example.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;

    @Override
    public Optional<Product> findById(Long id) {
        log.info("Find product with id: {}", id);
        return repository.findById(id);
    }

    @Override
    public Optional<List<Product>> findAll() {
        log.info("Find all products");
        return Optional.of(repository.findAll());
    }


    @Override
    public void update(Product product) {
        log.info("Update product: {}", product);
        repository.save(product);
    }


    @Override
    public Product save(Product product) {
        // Set the product version to 1 as we're adding a new product to the database
        product.setVersion(1L);
        log.info("Save product to the database: {}", product);
        return repository.save(product);
    }

    @Override
    public void delete(Long id) {
        log.info("Delete product with id: {}", id);
        repository.deleteById(id);
    }
}

package com.example.product.controller;

import com.example.product.entity.Product;
import com.example.product.exception.ResourceNotFoundException;
import com.example.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private URI getUri(Product product) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(product.getId()).toUri();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        final Product product = productService.findProductById(id);
        URI location = getUri(product);
        return ResponseEntity.ok()
                .eTag(String.valueOf(product.getVersion()))
                .location(location)
                .body(product);

    }

    @GetMapping
    public ResponseEntity<?> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@Valid @RequestBody Product product) {
        final Product newProduct = productService.save(product);
        URI location = getUri(product);

        // Build a created response
        return ResponseEntity
                .created(location)
                .eTag(String.valueOf(newProduct.getVersion()))
                .body(newProduct);

    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody Product product, @PathVariable Long id) throws ResourceNotFoundException {
        final Product existingProduct = productService.findProductById(id);

        existingProduct.setName(product.getName());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setVersion(existingProduct.getVersion() + 1);
        URI location = getUri(existingProduct);

        // Update the product and return an ok response
        productService.update(existingProduct);
        return ResponseEntity.ok()
                .location(location)
                .eTag(String.valueOf(existingProduct.getVersion()))
                .body(existingProduct);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

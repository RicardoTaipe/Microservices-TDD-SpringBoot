package com.example.productservice.web;

import com.example.productservice.domain.Product;
import com.example.productservice.service.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductServiceImpl productService;

    @GetMapping("{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        final Optional<Product> product = productService.findById(id);
        if (product.isPresent()) {
            try {
                return ResponseEntity.ok()
                        .eTag(String.valueOf(product.get().getVersion()))
                        .location(new URI("/product" + product.get().getId()))
                        .body(product.get());
            } catch (URISyntaxException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<?> getProducts() {
        final Optional<List<Product>> products = productService.findAll();
        return products.isPresent() ? ResponseEntity.ok(products.get()) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        final Product newProduct = productService.save(product);
        try {
            // Build a created response
            return ResponseEntity
                    .created(new URI("/product/" + newProduct.getId()))
                    .eTag(String.valueOf(newProduct.getVersion()))
                    .body(newProduct);
        } catch (URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<?> updateProduct(@RequestBody Product product, @PathVariable Long id) {
        final Optional<Product> existingProduct = productService.findById(id);
        if (existingProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        existingProduct.get().setName(product.getName());
        existingProduct.get().setQuantity(product.getQuantity());
        existingProduct.get().setVersion(existingProduct.get().getVersion() + 1L);

        try {
            // Update the product and return an ok response
            productService.update(existingProduct.get());
            return ResponseEntity.ok()
                    .location(new URI("/product/" + existingProduct.get().getId()))
                    .eTag(String.valueOf(existingProduct.get().getVersion()))
                    .body(existingProduct);
        } catch (URISyntaxException e) {
            // An error occurred trying to create the location URI, return an error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Optional<Product> existingProduct = productService.findById(id);
        if (existingProduct.isPresent()) {
            productService.delete(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}

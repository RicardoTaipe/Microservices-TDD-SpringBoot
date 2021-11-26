package com.example.productservice.repository;

import com.example.productservice.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void shouldAddNewProduct(){
        final Product product = new Product(1L,"lorem",1L,2L);
        final Product expected = productRepository.save(product);
        assertThat(expected).isEqualTo(product);
    }

}
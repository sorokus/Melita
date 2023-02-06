package com.melita.ordermanagement.repository;

import com.melita.ordermanagement.model.entity.Product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * @author sorokus.dev@gmail.com
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    ProductRepository repository;

    @Test
    void findAllByOrderByIdAsc() {
        List<Product> products = repository.findAllByOrderByIdAsc();
        assertThat(products).isNotNull().hasSize(4);
    }

    @Test
    void findDistinctByPackagesIsIn() {
        List<Product> products = repository.findDistinctByPackagesIsIn(List.of(1L, 2L));
        assertThat(products).isNotNull().hasSize(1);
    }

    @Test
    void findDistinctByPackagesIsInV2() {
        List<Product> products = repository.findDistinctByPackagesIsIn(List.of(1L, 2L, 3L, 4L, 7L, 8L));
        assertThat(products).isNotNull().hasSize(3);
    }

    @Test
    void findDistinctByPackagesIsInV3() {
        List<Product> products = repository.findDistinctByPackagesIsIn(List.of(1L, 2L, 3L, 5L));
        assertThat(products).isNotNull().hasSize(3);
    }

    @Test
    void findDistinctByPackagesIsInAndIsApprovable() {
        List<Product> products = repository.findDistinctByPackagesIsInAndIsApprovable(List.of(1L, 2L, 5L), true);
        assertThat(products).isNotNull().hasSize(1);
    }

    @Test
    void findDistinctByPackagesIsInAndIsApprovableV2() {
        List<Product> products = repository.findDistinctByPackagesIsInAndIsApprovable(List.of(1L, 2L, 5L, 7L, 3L), true);
        assertThat(products).isNotNull().hasSize(2);
    }

}
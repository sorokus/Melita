package com.melita.ordermanagement.repository;

import com.melita.ordermanagement.model.entity.Order;
import com.melita.ordermanagement.model.entity.Product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * @author sorokus.dev@gmail.com
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    OrderRepository repository;

    @Test
    void findByIdAndApprovedByIsNull() {
        Optional<Order> order = repository.findByIdAndApprovedByIsNull(1L);
        assertThat(order).isNotNull().isEmpty();
    }

    @Test
    void findByAll() {
        Iterable<Order> orders = repository.findAll();
        assertThat(orders).isNotNull().hasSize(0);
    }

}
package com.melita.ordermanagement.repository;

import com.melita.ordermanagement.model.entity.Package;
import com.melita.ordermanagement.model.entity.Product;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * @author sorokus.dev@gmail.com
 */

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class PackageRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PackageRepository repository;

    @Test
    void findAll() {
        Iterable<Package> packages = repository.findAll();
        assertThat(packages).isNotNull().hasSize(8);
    }

    @Test
    void findById() {
        Optional<Package> pack = repository.findById(1L);
        assertThat(pack).isNotNull().isNotEmpty().get().hasFieldOrPropertyWithValue("name", "250Mbps");
    }

    @Test
    void findByIdV2() {
        Optional<Package> pack = repository.findById(3L);
        assertThat(pack).isNotNull().isNotEmpty().get().hasFieldOrPropertyWithValue("name", "90 Channels");
    }

    @Test
    void findAllById() {
        Iterable<Package> packages = repository.findAllById(Set.of(1L, 3L, 10L, 4L, 15L));
        assertThat(packages).isNotNull().hasSize(3);
    }

}
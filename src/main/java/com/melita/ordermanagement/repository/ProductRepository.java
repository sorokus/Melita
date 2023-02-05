package com.melita.ordermanagement.repository;

import com.melita.ordermanagement.model.entity.Product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * @author sorokus.dev@gmail.com
 */

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findAllByOrderByIdAsc();
    List<Product> findDistinctByPackagesIsIn(Iterable<Long> packageIds);
    List<Product> findDistinctByPackagesIsInAndIsApprovable(Iterable<Long> packageIds, Boolean isApprovable);
}

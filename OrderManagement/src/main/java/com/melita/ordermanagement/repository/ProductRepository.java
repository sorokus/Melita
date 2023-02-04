package com.melita.ordermanagement.repository;

import com.melita.ordermanagement.model.entity.Product;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;

/*
 * @author sorokus.dev@gmail.com
 */
public interface ProductRepository extends CrudRepository<Product, Long>, QueryByExampleExecutor<Product> {
    List<Product> findAllByOrderByIdAsc();
    List<Product> findDistinctByPackagesIsInAndIsApprovable(Iterable<Long> packageIds, Boolean isApprovable);
}

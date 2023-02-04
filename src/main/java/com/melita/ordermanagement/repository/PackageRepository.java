package com.melita.ordermanagement.repository;

import com.melita.ordermanagement.model.entity.Package;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

/*
 * @author sorokus.dev@gmail.com
 */
public interface PackageRepository extends CrudRepository<Package, Long>, QueryByExampleExecutor<Package> {
}

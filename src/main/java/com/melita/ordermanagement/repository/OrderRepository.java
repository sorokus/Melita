package com.melita.ordermanagement.repository;

import com.melita.ordermanagement.model.entity.Order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/*
 * @author sorokus.dev@gmail.com
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}

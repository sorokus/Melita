package com.melita.ordermanagement.repository;

import com.melita.ordermanagement.model.entity.Order;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/*
 * @author sorokus.dev@gmail.com
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    Optional<Order> findByIdAndApprovedByIsNull(Long orderId);
}

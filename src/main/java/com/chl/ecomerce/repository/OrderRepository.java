package com.chl.ecomerce.repository;

import com.chl.ecomerce.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}

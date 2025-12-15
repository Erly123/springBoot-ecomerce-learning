package com.chl.ecomerce.repository;

import com.chl.ecomerce.entities.OrderItemEntity;
import com.chl.ecomerce.entities.OrderItemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, OrderItemId> {
}

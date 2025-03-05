package com.krenai.order_management.repository;

import com.krenai.order_management.entity.Order;
import com.krenai.order_management.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStatus(OrderStatus status);
}
package com.krenai.order_management.service;

import com.krenai.order_management.dto.OrderDTO;
import com.krenai.order_management.entity.Order;
import com.krenai.order_management.entity.OrderStatus;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderDTO orderDTO);

    Order getOrder(Long id);

    Order updateOrder(Long id, OrderDTO orderDTO);

    void deleteOrder(Long id);

    List<Order> listOrders(OrderStatus status);

    List<Order> getAllOrders();
}

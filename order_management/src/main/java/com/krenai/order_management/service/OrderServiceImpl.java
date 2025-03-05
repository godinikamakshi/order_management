package com.krenai.order_management.service;

import com.krenai.order_management.dto.OrderDTO;
import com.krenai.order_management.entity.Order;
import com.krenai.order_management.entity.OrderItem;
import com.krenai.order_management.entity.OrderStatus;
import com.krenai.order_management.repository.OrderItemRepository;
import com.krenai.order_management.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderServiceImpl(OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Order createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setCustomerName(orderDTO.getCustomerName());
        order.setStatus(orderDTO.getStatus());

        List<OrderItem> items = orderDTO.getItems().stream().map(itemDTO -> {
            OrderItem item = new OrderItem();
            item.setProductName(itemDTO.getProductName());
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(itemDTO.getPrice());
            item.setOrder(order);
            return item;
        }).toList();

        order.setItems(items);
        double totalAmount = items.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        order.setTotalAmount(totalAmount);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrder(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Override
    public Order updateOrder(Long id, OrderDTO orderDTO) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));

        existingOrder.setCustomerName(orderDTO.getCustomerName());
        existingOrder.setStatus(orderDTO.getStatus());

        // Update Order Items
        List<OrderItem> updatedItems = orderDTO.getItems().stream().map(itemDTO -> {
            OrderItem item = new OrderItem();
            item.setProductName(itemDTO.getProductName());
            item.setQuantity(itemDTO.getQuantity());
            item.setPrice(itemDTO.getPrice());
            item.setOrder(existingOrder);
            return item;
        }).toList();

        existingOrder.getItems().clear();
        existingOrder.getItems().addAll(updatedItems);

        double totalAmount = updatedItems.stream().mapToDouble(item -> item.getPrice() * item.getQuantity()).sum();
        existingOrder.setTotalAmount(totalAmount);

        return orderRepository.save(existingOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + id));

        orderItemRepository.deleteAll(order.getItems());
        orderRepository.delete(order);
    }

    @Override
    public List<Order> listOrders(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
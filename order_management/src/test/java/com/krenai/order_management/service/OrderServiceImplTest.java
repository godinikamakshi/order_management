package com.krenai.order_management.service;

import com.krenai.order_management.dto.OrderDTO;
import com.krenai.order_management.dto.OrderItemDTO;
import com.krenai.order_management.entity.Order;
import com.krenai.order_management.entity.OrderItem;
import com.krenai.order_management.entity.OrderStatus;
import com.krenai.order_management.repository.OrderItemRepository;
import com.krenai.order_management.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {
    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderItemRepository orderItemRepository;

    private Order order;
    private OrderDTO orderDTO;
    private OrderItem orderItem;
    private OrderItemDTO orderItemDTO;

    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        order.setCustomerName("John Doe");
        order.setStatus(OrderStatus.COMPLETED);

        orderItem = new OrderItem();
        orderItem.setId(1L);
        orderItem.setProductName("Laptop");
        orderItem.setQuantity(1);
        orderItem.setPrice(1000.0);
        orderItem.setOrder(order);

        order.setItems(List.of(orderItem));
        order.setTotalAmount(1000.0);

        orderItemDTO = new OrderItemDTO();
        orderItemDTO.setProductName("Laptop");
        orderItemDTO.setQuantity(1);
        orderItemDTO.setPrice(1000.0);

        orderDTO = new OrderDTO();
        orderDTO.setCustomerName("John Doe");
        orderDTO.setStatus(OrderStatus.COMPLETED);
        orderDTO.setItems(List.of(orderItemDTO));
    }

    @Test
    void testCreateOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        Order createdOrder = orderService.createOrder(orderDTO);
        assertNotNull(createdOrder);
        assertEquals("John Doe", createdOrder.getCustomerName());
        assertEquals(1000.0, createdOrder.getTotalAmount());
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testGetOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Order retrievedOrder = orderService.getOrder(1L);
        assertNotNull(retrievedOrder);
        verify(orderRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateOrder() {
        order.setItems(new ArrayList<>(List.of(orderItem)));
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        Order updatedOrder = orderService.updateOrder(1L, orderDTO);
        assertNotNull(updatedOrder);
        assertEquals("John Doe", updatedOrder.getCustomerName());
        assertEquals(1000.0, updatedOrder.getTotalAmount());
        verify(orderRepository, times(1)).findById(1L);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testDeleteOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        doNothing().when(orderItemRepository).deleteAll(order.getItems());
        doNothing().when(orderRepository).delete(order);
        orderService.deleteOrder(1L);
        verify(orderRepository, times(1)).findById(1L);
        verify(orderItemRepository, times(1)).deleteAll(order.getItems());
        verify(orderRepository, times(1)).delete(order);
    }

    @Test
    void testListOrders() {
        when(orderRepository.findByStatus(OrderStatus.COMPLETED)).thenReturn(List.of(order));
        List<Order> orders = orderService.listOrders(OrderStatus.COMPLETED);
        assertNotNull(orders);
        assertEquals(1, orders.size());
        verify(orderRepository, times(1)).findByStatus(OrderStatus.COMPLETED);
    }

    @Test
    void testGetAllOrders() {
        when(orderRepository.findAll()).thenReturn(List.of(order));
        List<Order> orders = orderService.getAllOrders();
        assertNotNull(orders);
        assertEquals(1, orders.size());
        verify(orderRepository, times(1)).findAll();
    }
}
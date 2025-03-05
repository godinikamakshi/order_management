package com.krenai.order_management.controller;

import com.krenai.order_management.dto.OrderDTO;
import com.krenai.order_management.entity.Order;
import com.krenai.order_management.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderControllerTest {
    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    private Order order;
    private OrderDTO orderDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        order = new Order();
        order.setId(1L);
        order.setTotalAmount(100.0);
        orderDTO = new OrderDTO();
    }

    @Test
    void testCreateOrder() {
        when(orderService.createOrder(any(OrderDTO.class))).thenReturn(order);
        ResponseEntity<Order> response = orderController.createOrder(orderDTO);
        assertNotNull(response.getBody());
        verify(orderService, times(1)).createOrder(any(OrderDTO.class));
    }

    @Test
    void testGetOrder() {
        when(orderService.getOrder(1L)).thenReturn(order);
        ResponseEntity<Order> response = orderController.getOrder(1L);
        assertNotNull(response.getBody());
        verify(orderService, times(1)).getOrder(1L);
    }

    @Test
    void testUpdateOrder() {
        when(orderService.updateOrder(eq(1L), any(OrderDTO.class))).thenReturn(order);
        ResponseEntity<Order> response = orderController.updateOrder(1L, orderDTO);
        assertNotNull(response.getBody());
        verify(orderService, times(1)).updateOrder(eq(1L), any(OrderDTO.class));
    }

    @Test
    void testDeleteOrder() {
        doNothing().when(orderService).deleteOrder(1L);
        ResponseEntity<String> response = orderController.deleteOrder(1L);
        assertEquals("Order deleted successfully", response.getBody());
        verify(orderService, times(1)).deleteOrder(1L);
    }

    @Test
    void testListOrders() {
        List<Order> orders = Collections.singletonList(order);
        when(orderService.listOrders(null)).thenReturn(orders);
        ResponseEntity<List<Order>> response = orderController.listOrders(null);
        assertNotNull(response.getBody());
        verify(orderService, times(1)).listOrders(null);
    }

    @Test
    void testGetAllOrders() {
        List<Order> orders = Collections.singletonList(order);
        when(orderService.getAllOrders()).thenReturn(orders);
        ResponseEntity<List<Order>> response = orderController.getAllOrders();
        assertNotNull(response.getBody());
        verify(orderService, times(1)).getAllOrders();
    }
}

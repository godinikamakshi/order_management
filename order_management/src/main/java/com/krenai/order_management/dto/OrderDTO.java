package com.krenai.order_management.dto;

import com.krenai.order_management.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String customerName;
    private List<OrderItemDTO> items;
    private OrderStatus status;
}
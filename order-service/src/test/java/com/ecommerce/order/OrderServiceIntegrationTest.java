package com.ecommerce.order;

import com.ecommerce.order.domain.Order;
import com.ecommerce.order.domain.OrderItem;
import com.ecommerce.order.domain.OrderStatus;
import com.ecommerce.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceIntegrationTest {

    @Autowired
    private OrderService orderService;

    @Test
    void shouldCreateOrderSuccessfully() {
        OrderItem item = new OrderItem("prod_1", 2, new BigDecimal("50.00"));
        Order order = new Order(null, "cust_123", List.of(item), new BigDecimal("100.00"), OrderStatus.PENDING, null);

        Order createdOrder = orderService.createOrder(order);

        assertNotNull(createdOrder.getId());
        assertEquals(new BigDecimal("100.00"), createdOrder.getTotalAmount());
        assertEquals(OrderStatus.PENDING, createdOrder.getStatus());
    }
}
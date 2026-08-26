package com.ecommerce.order;

import com.ecommerce.order.domain.Order;
import com.ecommerce.order.domain.OrderItem;
import com.ecommerce.order.dto.OrderRequest;
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
        // 1. Instancia o item da requisição
        OrderItem item = new OrderItem("prod-001", 2, new BigDecimal("49.90"));

        // 2. Cria o DTO OrderRequest esperado pelo OrderService
        OrderRequest request = new OrderRequest("user-123", List.of(item));

        // 3. Executa a criação do pedido
        Order createdOrder = orderService.createOrder(request);

        // 4. Valida os resultados
        assertNotNull(createdOrder.getId());
        assertEquals("user-123", createdOrder.getCustomerId());
        assertEquals("CREATED", createdOrder.getStatus());
        assertEquals(new BigDecimal("99.80"), createdOrder.getTotalAmount());
    }
}
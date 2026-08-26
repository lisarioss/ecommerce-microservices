package com.ecommerce.order.service;

import com.ecommerce.order.domain.Order;
import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.repository.OrderRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    public OrderService(OrderRepository orderRepository, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Order createOrder(OrderRequest request) {
        BigDecimal totalAmount = request.items().stream()
            .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        Order newOrder = new Order(
            null,
            request.customerId(),
            request.items(),
            totalAmount,
            "CREATED",
            LocalDateTime.now()
        );

        Order savedOrder = orderRepository.save(newOrder);
        rabbitTemplate.convertAndSend("", "order-created", savedOrder.getId());

        return savedOrder;
    }

    // Adicione este método para resolver o erro do Listener:
    @SuppressWarnings("null")
    public void updateOrderStatus(String orderId, String status) {
        orderRepository.findById(orderId).ifPresent(order -> {
            order.setStatus(status);
            orderRepository.save(order);
        });
    }
}
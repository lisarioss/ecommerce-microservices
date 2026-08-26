package com.ecommerce.order.service;

import com.ecommerce.order.config.RabbitMQConfig;
import com.ecommerce.order.domain.Order;
import com.ecommerce.order.domain.OrderStatus;
import com.ecommerce.order.dto.OrderCreatedEvent;
import com.ecommerce.order.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    public OrderService(OrderRepository orderRepository, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = new OrderCreatedEvent(
            savedOrder.getId(),
            savedOrder.getCustomerId(),
            savedOrder.getTotalAmount()
        );

        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_CREATED_QUEUE, event);
        return savedOrder;
    }

    @SuppressWarnings("null")
    public void updateOrderStatus(String orderId, String status) {
        orderRepository.findById(orderId).ifPresent(order -> {
            try {
                order.setStatus(OrderStatus.valueOf(status));
                orderRepository.save(order);
            } catch (IllegalArgumentException e) {
                // Trata caso o status receba uma string inválida
                order.setStatus(OrderStatus.REJECTED);
                orderRepository.save(order);
            }
        });
    }
}
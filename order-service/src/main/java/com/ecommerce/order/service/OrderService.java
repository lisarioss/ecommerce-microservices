package com.ecommerce.order.service;

import com.ecommerce.order.domain.Order;
import com.ecommerce.order.dto.OrderCreatedEvent;
import com.ecommerce.order.repository.OrderRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RabbitTemplate rabbitTemplate;

    public OrderService(OrderRepository orderRepository, RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    public Order createOrder(Order orderRequest) {
        Order pendingOrder = new Order(
            null,
            orderRequest.customerId(),
            orderRequest.items(),
            orderRequest.totalAmount(),
            Order.OrderStatus.PENDING,
            Instant.now()
        );

        Order savedOrder = orderRepository.save(pendingOrder);

        OrderCreatedEvent event = new OrderCreatedEvent(
            savedOrder.id(),
            savedOrder.customerId(),
            savedOrder.totalAmount()
        );
        
        rabbitTemplate.convertAndSend("orders.exchange", "order.created", event);

        return savedOrder;
    }
}
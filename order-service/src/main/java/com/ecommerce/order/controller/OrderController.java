package com.ecommerce.order.controller;

import com.ecommerce.order.domain.Order;
import com.ecommerce.order.dto.OrderRequest;
import com.ecommerce.order.repository.OrderRepository;
import com.ecommerce.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    public OrderController(OrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request) {
        Order newOrder = new Order(
            null,
            request.customerId(),
            request.items(),
            request.totalAmount(),
            null,
            null
        );

        Order createdOrder = orderService.createOrder(newOrder);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    @GetMapping("/{id}")
    @SuppressWarnings("null")
    public ResponseEntity<Order> getOrderById(@PathVariable String id) {
        return orderRepository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }
}
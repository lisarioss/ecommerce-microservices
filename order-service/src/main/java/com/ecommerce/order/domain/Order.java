package com.ecommerce.order.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Document(collection = "orders")
public record Order(
    @Id String id,
    String customerId,
    List<OrderItem> items,
    BigDecimal totalAmount,
    OrderStatus status,
    Instant createdAt
) {
    public enum OrderStatus {
        PENDING, PAID, FAILED, CANCELLED
    }
}

package com.ecommerce.order.dto;

import java.math.BigDecimal;

public record OrderCreatedEvent(
    String orderId,
    String customerId,
    BigDecimal totalAmount
) {}
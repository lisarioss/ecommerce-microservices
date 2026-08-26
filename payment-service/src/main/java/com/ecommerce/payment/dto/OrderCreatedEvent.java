package com.ecommerce.payment.dto;

import java.math.BigDecimal;

public record OrderCreatedEvent(
    String orderId,
    String customerId,
    BigDecimal totalAmount
) {}
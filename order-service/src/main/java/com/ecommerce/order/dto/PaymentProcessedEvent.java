package com.ecommerce.order.dto;

public record PaymentProcessedEvent(
    String orderId,
    String status
) {}
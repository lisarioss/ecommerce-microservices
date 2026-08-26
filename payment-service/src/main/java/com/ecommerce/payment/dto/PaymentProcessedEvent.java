package com.ecommerce.payment.dto;

public record PaymentProcessedEvent(
    String orderId,
    String status // "APPROVED" ou "REJECTED"
) {}
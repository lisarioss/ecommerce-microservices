package com.ecommerce.order.dto;

public record PaymentResultMessage(
    String orderId,
    String status
) {}
package com.ecommerce.order.domain;

import java.math.BigDecimal;

public record OrderItem(
    String productId,
    Integer quantity,
    BigDecimal unitPrice
) {}
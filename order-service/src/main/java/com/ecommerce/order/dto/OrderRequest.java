package com.ecommerce.order.dto;

import com.ecommerce.order.domain.OrderItem;
import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
    String customerId,
    List<OrderItem> items,
    BigDecimal totalAmount
) {}
package com.ecommerce.order.listener;

import com.ecommerce.order.config.RabbitMQConfig;
import com.ecommerce.order.dto.PaymentResultMessage;
import com.ecommerce.order.service.OrderService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentResultListener {

    private final OrderService orderService;

    public PaymentResultListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_PROCESSED_QUEUE)
    public void handlePaymentResult(PaymentResultMessage message) {
        orderService.updateOrderStatus(message.orderId(), message.status());
    }
}
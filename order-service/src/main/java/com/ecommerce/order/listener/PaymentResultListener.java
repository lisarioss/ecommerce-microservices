package com.ecommerce.order.listener;

import com.ecommerce.order.config.RabbitMQConfig;
import com.ecommerce.order.dto.PaymentProcessedEvent;
import com.ecommerce.order.service.OrderService;
import java.util.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentResultListener {

    private static final Logger log = Logger.getLogger(PaymentResultListener.class.getName());

    private final OrderService orderService;

    public PaymentResultListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = RabbitMQConfig.PAYMENT_PROCESSED_QUEUE)
    public void handlePaymentProcessed(PaymentProcessedEvent event) {
        log.info(String.format("Atualizando status do pedido %s para %s", event.orderId(), event.status()));
        orderService.updateOrderStatus(event.orderId(), event.status());
    }
}
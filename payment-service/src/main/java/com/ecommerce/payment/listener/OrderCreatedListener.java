package com.ecommerce.payment.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class OrderCreatedListener {

    @RabbitListener(queues = "order-created")
    public void handleOrderCreated(Object orderEvent) {
        log.info("Mensagem recebida do RabbitMQ: {}", orderEvent);
        // Lógica de processamento do pagamento
    }
}

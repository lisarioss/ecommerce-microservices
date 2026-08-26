package com.ecommerce.payment.listener;

import com.ecommerce.payment.config.RabbitMQConfig;
import com.ecommerce.payment.dto.OrderCreatedEvent;
import java.util.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    private static final Logger log = Logger.getLogger(PaymentListener.class.getName());

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info(String.format("Recebido pedido para processamento de pagamento. OrderId: %s, Valor: R$%s", 
                event.orderId(), event.totalAmount()));

        // Lógica simulada de processamento de pagamento
        boolean paymentApproved = true; 

        if (paymentApproved) {
            log.info(String.format("Pagamento APROVADO com sucesso para o pedido: %s", event.orderId()));
        } else {
            log.warning(String.format("Pagamento RECUSADO para o pedido: %s", event.orderId()));
        }
    }
}
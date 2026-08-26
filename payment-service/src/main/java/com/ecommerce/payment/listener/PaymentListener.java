package com.ecommerce.payment.listener;

import com.ecommerce.payment.config.RabbitMQConfig;
import com.ecommerce.payment.dto.OrderCreatedEvent;
import com.ecommerce.payment.dto.PaymentProcessedEvent;
import java.util.logging.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {

    private static final Logger log = Logger.getLogger(PaymentListener.class.getName());

    private final RabbitTemplate rabbitTemplate;

    public PaymentListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.ORDER_CREATED_QUEUE)
    public void handleOrderCreated(OrderCreatedEvent event) {
        log.info(String.format("Recebido pedido para processamento de pagamento. OrderId: %s, Valor: R$%s", 
                event.orderId(), event.totalAmount()));

        // Lógica simulada de processamento de pagamento
        boolean paymentApproved = true; 
        String paymentStatus = paymentApproved ? "APPROVED" : "REJECTED";

        if (paymentApproved) {
            log.info(String.format("Pagamento APROVADO com sucesso para o pedido: %s", event.orderId()));
        } else {
            log.warning(String.format("Pagamento RECUSADO para o pedido: %s", event.orderId()));
        }

        PaymentProcessedEvent responseEvent = new PaymentProcessedEvent(event.orderId(), paymentStatus);
        rabbitTemplate.convertAndSend(RabbitMQConfig.PAYMENT_PROCESSED_QUEUE, responseEvent);
        log.info(String.format("Evento de pagamento processado enviado para a fila %s", RabbitMQConfig.PAYMENT_PROCESSED_QUEUE));
    }
}
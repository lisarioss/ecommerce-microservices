package com.ecommerce.order.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Nomes de fila alinhados com o payment-service (ver payment.config.RabbitMQConfig)
    public static final String ORDER_CREATED_QUEUE = "order.created";
    public static final String PAYMENT_PROCESSED_QUEUE = "payment.processed";

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue(ORDER_CREATED_QUEUE, true);
    }

    @Bean
    public Queue paymentProcessedQueue() {
        return new Queue(PAYMENT_PROCESSED_QUEUE, true);
    }

    // O Spring Boot injeta este bean automaticamente no RabbitTemplate e nos
    // listeners, permitindo publicar/consumir objetos (ex.: OrderCreatedEvent)
    // como JSON em vez de apenas texto/bytes.
    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}

package com.ecommerce.order.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue orderCreatedQueue() {
        return new Queue("order-created", true);
    }

    @Bean
    public Queue paymentResultQueue() {
        return new Queue("payment-result-queue", true);
    }
}
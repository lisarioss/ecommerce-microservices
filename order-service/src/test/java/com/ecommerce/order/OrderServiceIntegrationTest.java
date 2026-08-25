package com.ecommerce.order;

import com.ecommerce.order.domain.Order;
import com.ecommerce.order.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Testcontainers
class OrderServiceIntegrationTest {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0");

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void shouldSaveOrderToMongoContainer() {
        Order order = new Order(null, "usr_123", List.of(), new BigDecimal("299.90"), Order.OrderStatus.PENDING, null);
        Order saved = orderRepository.save(order);

        assertThat(saved.id()).isNotNull();
        assertThat(saved.totalAmount()).isEqualTo(new BigDecimal("299.90"));
    }
}
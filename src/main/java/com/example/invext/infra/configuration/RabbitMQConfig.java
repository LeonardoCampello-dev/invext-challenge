package com.example.invext.infra.configuration;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
  public static final String TICKET_QUEUE_NAME = "ticket_queue";

  @Bean
  public Queue ticketQueue() {
    return new Queue(TICKET_QUEUE_NAME);
  }
}

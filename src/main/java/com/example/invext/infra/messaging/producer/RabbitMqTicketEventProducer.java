package com.example.invext.infra.messaging.producer;

import com.example.invext.application.contract.ITicketEventProducer;
import com.example.invext.domain.customerservicecenter.entity.Ticket;
import com.example.invext.infra.configuration.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqTicketEventProducer implements ITicketEventProducer {
  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Override
  public void enqueueTicket(Ticket ticket) {
    rabbitTemplate.convertAndSend(
        RabbitMQConfig.TICKET_QUEUE_NAME,
        ticket
    );

    System.out.println("Ticket enfileirado");
  }

  @Override
  public void enqueueTicketWithPriority(
      Ticket ticket
  ) {
    rabbitTemplate.convertAndSend(
        RabbitMQConfig.TICKET_QUEUE_NAME,
        ticket,
        message -> {
          message
              .getMessageProperties()
              .setPriority(ticket
                               .getWaitingTimeInSeconds()
                               .intValue());

          return message;
        }
    );

    System.out.println("Ticket enfileirado novamente com prioridade maior");
  }
}
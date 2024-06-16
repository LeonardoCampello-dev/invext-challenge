package com.example.invext.infra.messaging.consumer;

import com.example.invext.application.usecase.ticket.AttemptLinkTicketToAvailableAttendantWithPriorityUseCase;
import com.example.invext.domain.customerservicecenter.entity.Ticket;
import com.example.invext.infra.configuration.RabbitMQConfig;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TicketConsumer {
  @Autowired
  private final AttemptLinkTicketToAvailableAttendantWithPriorityUseCase useCase;

  @RabbitListener(queues = RabbitMQConfig.TICKET_QUEUE_NAME)
  public void handleTicket(Ticket ticket) {
    useCase.execute(ticket);
  }
}

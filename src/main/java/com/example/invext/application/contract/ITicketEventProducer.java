package com.example.invext.application.contract;

import com.example.invext.domain.customerservicecenter.entity.Ticket;

public interface ITicketEventProducer {
  void enqueueTicket(Ticket ticket);

  void enqueueTicketWithPriority(Ticket ticket);
}

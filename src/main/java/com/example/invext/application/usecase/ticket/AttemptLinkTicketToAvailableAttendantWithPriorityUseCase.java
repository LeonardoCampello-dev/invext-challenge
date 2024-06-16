package com.example.invext.application.usecase.ticket;

import com.example.invext.application.contract.ITicketEventProducer;
import com.example.invext.domain.customerservicecenter.entity.Attendant;
import com.example.invext.domain.customerservicecenter.entity.Ticket;
import com.example.invext.domain.customerservicecenter.service.TicketOpeningService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AttemptLinkTicketToAvailableAttendantWithPriorityUseCase {
  @Autowired
  private TicketOpeningService ticketOpeningService;
  @Autowired
  private ITicketEventProducer ticketEventProducer;

  public void execute(Ticket ticket) {
    Optional<Attendant> attendant = ticketOpeningService.findAvailableAttendant(ticket.getDepartment());

    if (attendant.isPresent()) {
      ticket.linkAttendant(attendant.get());
    } else {
      ticketEventProducer.enqueueTicketWithPriority(ticket);
    }
  }
}

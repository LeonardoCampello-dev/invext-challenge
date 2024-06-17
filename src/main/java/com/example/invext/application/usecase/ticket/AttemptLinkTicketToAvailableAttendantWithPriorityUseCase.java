package com.example.invext.application.usecase.ticket;

import com.example.invext.application.contract.ITicketEventProducer;
import com.example.invext.domain.customerservicecenter.entity.Attendant;
import com.example.invext.domain.customerservicecenter.entity.Ticket;
import com.example.invext.domain.customerservicecenter.repository.ITicketRepository;
import com.example.invext.domain.customerservicecenter.service.TicketOpeningService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AttemptLinkTicketToAvailableAttendantWithPriorityUseCase {
  @Autowired
  private final TicketOpeningService ticketOpeningService;
  @Autowired
  private final ITicketEventProducer ticketEventProducer;
  @Autowired
  private final ITicketRepository ticketRepository;

  public void execute(Integer ticketId) {
    Optional<Ticket> ticket = ticketRepository.findById(ticketId);

    // TODO poderia adicionar algum tratamento
    if (ticket.isEmpty()) return;

    Optional<Attendant> attendant = ticketOpeningService.findAvailableAttendant(ticket
                                                                                    .get()
                                                                                    .getDepartment());

    if (attendant.isPresent()) {
      ticket
          .get()
          .linkAttendant(attendant.get());
    } else {
      ticketEventProducer.enqueueTicketWithPriority(ticket.get());
    }
  }
}

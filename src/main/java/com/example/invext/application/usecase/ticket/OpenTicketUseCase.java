package com.example.invext.application.usecase.ticket;


import com.example.invext.application.contract.ITicketEventProducer;
import com.example.invext.domain.customerservicecenter.entity.Attendant;
import com.example.invext.domain.customerservicecenter.entity.Customer;
import com.example.invext.domain.customerservicecenter.entity.Ticket;
import com.example.invext.domain.customerservicecenter.repository.ICustomerRepository;
import com.example.invext.domain.customerservicecenter.repository.ITicketRepository;
import com.example.invext.domain.customerservicecenter.service.TicketOpeningService;
import com.example.invext.infra.rest.dto.OpenTicketDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OpenTicketUseCase {
  @Autowired
  private final ITicketRepository ticketRepository;
  @Autowired
  private final ICustomerRepository customerRepository;
  @Autowired
  private final ITicketEventProducer ticketEventProducer;
  @Autowired
  private final TicketOpeningService ticketOpeningService;

  public Ticket execute(OpenTicketDTO dto) {
    // TODO poderia aplicar alguma regra para validação de ticket existente

    Optional<Customer> customer = customerRepository.findById(dto.customerId());

    // TODO tratar para retornar resultado negativo
    if (!customer.isPresent()) return null;

    Ticket ticket = new Ticket(
        dto.department(),
        dto.title(),
        dto.description(),
        customer.get()
    );

    Optional<Attendant> attendant = this.ticketOpeningService.findAvailableAttendant(ticket.getDepartment());

    if (attendant.isPresent()) {
      ticket.linkAttendant(attendant.get());
    } else {
      ticketEventProducer.enqueueTicket(ticket);
    }

    ticketRepository.save(ticket);

    return ticket;
  }
}

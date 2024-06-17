package com.example.invext.application.usecase.ticket;

import com.example.invext.application.contract.ITicketEventProducer;
import com.example.invext.domain.customerservicecenter.entity.Attendant;
import com.example.invext.domain.customerservicecenter.entity.Customer;
import com.example.invext.domain.customerservicecenter.entity.Ticket;
import com.example.invext.domain.customerservicecenter.enumeration.Department;
import com.example.invext.domain.customerservicecenter.repository.ICustomerRepository;
import com.example.invext.domain.customerservicecenter.repository.ITicketRepository;
import com.example.invext.domain.customerservicecenter.service.TicketOpeningService;
import com.example.invext.infra.rest.dto.OpenTicketDTO;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class OpenTicketUseCaseTest {

  @Mock
  private ITicketRepository ticketRepository;

  @Mock
  private ICustomerRepository customerRepository;

  @Mock
  private ITicketEventProducer ticketEventProducer;

  @Mock
  private TicketOpeningService ticketOpeningService;

  @InjectMocks
  private OpenTicketUseCase openTicketUseCase;

  private Faker faker = new Faker();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void shouldCreateTicketAndAssignAttendantWhenAttendantIsAvailable() {
    OpenTicketDTO dto = new OpenTicketDTO(
        Department.CREDIT_CARDS,
        faker
            .lorem()
            .sentence(),
        faker
            .lorem()
            .paragraph(),
        1
    );

    Customer customer = new Customer(
        1,
        faker
            .name()
            .fullName(),
        faker
            .internet()
            .emailAddress(),
        new ArrayList<>()
    );

    Attendant attendant = new Attendant(
        1,
        faker
            .name()
            .fullName(),
        faker
            .internet()
            .emailAddress(),
        Department.CREDIT_CARDS,
        new ArrayList<>()
    );

    Ticket ticket = new Ticket(
        dto.department(),
        dto.title(),
        dto.description(),
        customer
    );

    ticket.linkAttendant(attendant);

    when(customerRepository.findById(dto.customerId())).thenReturn(Optional.of(customer));
    when(ticketOpeningService.findAvailableAttendant(dto.department())).thenReturn(Optional.of(attendant));
    when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

    Ticket createdTicket = openTicketUseCase.execute(dto);

    assertNotNull(createdTicket);

    assertEquals(
        dto.department(),
        createdTicket.getDepartment()
    );

    assertEquals(
        dto.title(),
        createdTicket.getTitle()
    );

    assertEquals(
        dto.description(),
        createdTicket.getDescription()
    );

    assertEquals(
        customer,
        createdTicket.getCustomer()
    );

    assertEquals(
        attendant,
        createdTicket.getAssignee()
    );

    verify(
        ticketRepository,
        times(1)
    ).save(any(Ticket.class));

    verify(
        ticketEventProducer,
        never()
    ).enqueueTicket(any(Ticket.class));
  }

  @Test
  public void shouldCreateTicketAndEnqueueWhenAttendantIsNotAvailable() {
    OpenTicketDTO dto = new OpenTicketDTO(
        Department.CREDIT_CARDS,
        faker
            .lorem()
            .sentence(),
        faker
            .lorem()
            .paragraph(),
        1
    );

    Customer customer = new Customer(
        1,
        faker
            .name()
            .fullName(),
        faker
            .internet()
            .emailAddress(),
        new ArrayList<>()
    );

    Ticket ticket = new Ticket(
        dto.department(),
        dto.title(),
        dto.description(),
        customer
    );

    when(customerRepository.findById(dto.customerId())).thenReturn(Optional.of(customer));
    when(ticketOpeningService.findAvailableAttendant(dto.department())).thenReturn(Optional.empty());
    when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

    Ticket createdTicket = openTicketUseCase.execute(dto);

    assertNotNull(createdTicket);

    assertEquals(
        dto.department(),
        createdTicket.getDepartment()
    );

    assertEquals(
        dto.title(),
        createdTicket.getTitle()
    );

    assertEquals(
        dto.description(),
        createdTicket.getDescription()
    );

    assertEquals(
        customer,
        createdTicket.getCustomer()
    );

    assertNull(createdTicket.getAssignee());

    verify(
        ticketRepository,
        times(1)
    ).save(any(Ticket.class));

    verify(
        ticketEventProducer,
        times(1)
    ).enqueueTicket(any(Ticket.class));
  }
}

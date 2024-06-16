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
import java.util.UUID;

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
        UUID
            .randomUUID()
            .toString()
    );

    Customer customer = new Customer(
        UUID
            .randomUUID()
            .toString(),
        faker
            .name()
            .fullName(),
        faker
            .internet()
            .emailAddress(),
        new ArrayList<>()
    );

    Attendant attendant = new Attendant(
        UUID
            .randomUUID()
            .toString(),
        faker
            .name()
            .fullName(),
        faker
            .internet()
            .emailAddress(),
        Department.CREDIT_CARDS,
        new ArrayList<>()
    );

    when(customerRepository.findById(dto.customerId())).thenReturn(Optional.of(customer));
    when(ticketOpeningService.findAvailableAttendant(dto.department())).thenReturn(Optional.of(attendant));

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
    ).save(createdTicket);

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
        UUID
            .randomUUID()
            .toString()
    );

    Customer customer = new Customer(
        UUID
            .randomUUID()
            .toString(),
        faker
            .name()
            .fullName(),
        faker
            .internet()
            .emailAddress(),
        new ArrayList<>()
    );

    when(customerRepository.findById(dto.customerId())).thenReturn(Optional.of(customer));
    when(ticketOpeningService.findAvailableAttendant(dto.department())).thenReturn(Optional.empty());

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
    ).save(createdTicket);

    verify(
        ticketEventProducer,
        times(1)
    ).enqueueTicket(createdTicket);
  }
}

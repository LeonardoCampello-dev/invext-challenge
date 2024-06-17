package com.example.invext.application.usecase.ticket;

import com.example.invext.application.contract.ITicketEventProducer;
import com.example.invext.domain.customerservicecenter.entity.Attendant;
import com.example.invext.domain.customerservicecenter.entity.Ticket;
import com.example.invext.domain.customerservicecenter.enumeration.Department;
import com.example.invext.domain.customerservicecenter.repository.ITicketRepository;
import com.example.invext.domain.customerservicecenter.service.TicketOpeningService;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class AttemptLinkTicketToAvailableAttendantWithPriorityUseCaseTest {

  @Mock
  private TicketOpeningService ticketOpeningService;

  @Mock
  private ITicketEventProducer ticketEventProducer;

  @Mock
  private ITicketRepository ticketRepository;

  @InjectMocks
  private AttemptLinkTicketToAvailableAttendantWithPriorityUseCase useCase;

  private Faker faker = new Faker();

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void shouldLinkAttendantWhenAvailable() {
    Ticket ticket = new Ticket(
        Department.CREDIT_CARDS,
        faker
            .lorem()
            .sentence(),
        faker
            .lorem()
            .paragraph(),
        null
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
        null
    );

    Integer ticketId = faker
        .number()
        .randomDigit();

    when(ticketOpeningService.findAvailableAttendant(ticket.getDepartment())).thenReturn(Optional.of(attendant));
    when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

    useCase.execute(ticketId);

    assertEquals(
        attendant,
        ticket.getAssignee()
    );

    verify(
        ticketEventProducer,
        never()
    ).enqueueTicketWithPriority(any(Ticket.class));
  }

  @Test
  public void shouldEnqueueWithPriorityWhenNoAttendantAvailable() {
    Ticket ticket = new Ticket(
        Department.CREDIT_CARDS,
        faker
            .lorem()
            .sentence(),
        faker
            .lorem()
            .paragraph(),
        null
    );

    Integer ticketId = faker
        .number()
        .randomDigit();

    when(ticketOpeningService.findAvailableAttendant(ticket.getDepartment())).thenReturn(Optional.empty());
    when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));

    useCase.execute(ticketId);

    assertNull(ticket.getAssignee());

    verify(
        ticketEventProducer,
        times(1)
    ).enqueueTicketWithPriority(ticket);
  }
}

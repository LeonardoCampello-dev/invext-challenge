package com.example.invext.application.usecase.ticket;

import com.example.invext.application.contract.ITicketEventProducer;
import com.example.invext.domain.customerservicecenter.entity.Attendant;
import com.example.invext.domain.customerservicecenter.entity.Ticket;
import com.example.invext.domain.customerservicecenter.enumeration.Department;
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
        null
    );

    when(ticketOpeningService.findAvailableAttendant(ticket.getDepartment())).thenReturn(Optional.of(attendant));

    useCase.execute(ticket);

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

    when(ticketOpeningService.findAvailableAttendant(ticket.getDepartment())).thenReturn(Optional.empty());

    useCase.execute(ticket);

    assertNull(ticket.getAssignee());

    verify(
        ticketEventProducer,
        times(1)
    ).enqueueTicketWithPriority(ticket);
  }
}

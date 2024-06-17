package com.example.invext.infra.database.mysql.datamapper;

import com.example.invext.domain.customerservicecenter.entity.Attendant;
import com.example.invext.domain.customerservicecenter.entity.Ticket;
import com.example.invext.infra.contract.IDataMapper;
import com.example.invext.infra.database.mysql.entity.AttendantJpaEntity;
import com.example.invext.infra.database.mysql.entity.TicketJpaEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TicketDataMapper implements IDataMapper<Ticket, TicketJpaEntity> {
  @Autowired
  private final AttendantDataMapper attendantDataMapper;
  @Autowired
  private final CustomerDataMapper customerDataMapper;

  @Override
  public Ticket toDomain(TicketJpaEntity ticketJpaEntity) {
    Attendant assigne = null;

    if (ticketJpaEntity.getAssignee() != null) {
      assigne = attendantDataMapper.toDomain(ticketJpaEntity.getAssignee());
    }

    return new Ticket(
        ticketJpaEntity.getId(),
        ticketJpaEntity.getDepartment(),
        ticketJpaEntity.getStatus(),
        ticketJpaEntity.getTitle(),
        ticketJpaEntity.getDescription(),
        ticketJpaEntity.getCreatedAt(),
        ticketJpaEntity.getUpdatedAt(),
        ticketJpaEntity.getClosedAt(),
        assigne,
        customerDataMapper.toDomain(ticketJpaEntity.getCustomer())

    );
  }

  @Override
  public TicketJpaEntity toEntity(Ticket ticket) {
    AttendantJpaEntity assigne = null;

    if (ticket.getAssignee() != null) {
      assigne = attendantDataMapper.toEntity(ticket.getAssignee());
    }

    return new TicketJpaEntity(
        ticket.getId(),
        ticket.getDepartment(),
        ticket.getStatus(),
        ticket.getTitle(),
        ticket.getDescription(),
        ticket.getCreatedAt(),
        ticket.getUpdatedAt(),
        ticket.getClosedAt(),
        assigne,
        customerDataMapper.toEntity(ticket.getCustomer())
    );
  }
}
